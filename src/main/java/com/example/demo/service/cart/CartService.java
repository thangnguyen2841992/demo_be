package com.example.demo.service.cart;

import com.example.demo.model.dto.cart.CartDetailDto;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.cart.Cart;
import com.example.demo.model.entity.cart.CartDetail;
import com.example.demo.model.entity.dish.Dish;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.DeliveryInfo;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderDetail;
import com.example.demo.repository.ICartRepository;
import com.example.demo.service.cartDetail.ICartDetailService;
import com.example.demo.service.dish.IDishService;
import com.example.demo.service.order.IOrderService;
import com.example.demo.service.orderDetail.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IDishService dishService;

    @Override
    public Iterable<Cart> findAll() {
        return this.cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return this.cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return this.cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {
            this.cartRepository.deleteById(id);
    }

    @Override
    public Optional<Cart> findCartByUserAndMerchant(User user, Merchant merchant) {
        return this.cartRepository.findCartByUserAndMerchant(user, merchant);
    }

    @Override
    public Iterable<Cart> findAllCartByUser(User user) {
        return this.cartRepository.findCartByUser(user);
    }

    @Override
    public Cart createCartWithUserAndMerchant(User user, Merchant merchant) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setMerchant(merchant);
        return cart;
    }

    @Override
    public CartDto getCartDtoByUserAndMerchant(User user, Merchant merchant) {
        Optional<Cart> findCart = cartRepository.findCartByUserAndMerchant(user, merchant);
        Cart cart;
        if (findCart.isPresent()) {
            cart = findCart.get();
        } else {
            cart = new Cart();
            cart.setUser(user);
            cart.setMerchant(merchant);
            cart = this.save(cart);
        }

        CartDto cartDto = makeCartDtoFromCart(cart);
        return cartDto;
    }

    @Override
    public List<CartDto> getAllCartDtoByUser(User user) {
        Iterable<Cart> carts = findAllCartByUser(user);
        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : carts) {
            List<CartDetail> cartDetails = (List<CartDetail>) cartDetailService.findAllByCart(cart);
            if (cartDetails.size() == 0) {
                cartRepository.deleteById(cart.getId());
                break;
            }
            CartDto cartDto = makeCartDtoFromCart(cart);
            cartDtos.add(cartDto);
        }
        return cartDtos;
    }

    @Override
    public CartDto addDishToCart(User user, CartDetailDto cartDetailDto) {
        Dish dish = cartDetailDto.getDish();
        Merchant merchant = dish.getMerchant();
        int quantity = cartDetailDto.getQuantity();
        CartDto cartDto = getCartDtoByUserAndMerchant(user, merchant);
        Long cartId = cartDto.getId();
        Cart cart = findById(cartId).get();

        Optional<CartDetail> findCartDetail = cartDetailService.findByCartAndDish(cart, dish);
        CartDetail cartDetail;
        if (findCartDetail.isPresent()) {
            cartDetail = findCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        } else {
            cartDetail = new CartDetail();
            cartDetail.setDish(dish);
            cartDetail.setCart(cart);
            cartDetail.setQuantity(quantity);
        }
        cartDetailService.save(cartDetail);
        cartDto = getCartDtoByUserAndMerchant(user, merchant);
        return cartDto;
    }

    @Override
    public boolean changeDishQuantityInCart(Cart cart, Dish dish, int amount) {
        Optional<CartDetail> findCartDetail = cartDetailService.findByCartAndDish(cart, dish);
        CartDetail cartDetail;
        if (findCartDetail.isPresent()){
            cartDetail = findCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + amount);
        } else {
            if (amount < 0) return false;
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setDish(dish);
            cartDetail.setQuantity(amount);
        }
        if (cartDetail.getQuantity() < 1){
            cartDetailService.deleteById(cartDetail.getId());
        } else {
            cartDetailService.save(cartDetail);
        }
        return true;
    }


    public CartDto makeCartDtoFromCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cart.setUser(cart.getUser());
        cartDto.setMerchant(cart.getMerchant());

        Iterable<CartDetail> cartDetails = cartDetailService.findAllByCart(cart);
        cartDetails.forEach(
                cartDetail -> {
                    CartDetailDto cartDetailDto = new CartDetailDto();
                    cartDetailDto.setDish(cartDetail.getDish());
                    cartDetailDto.setQuantity(cartDetail.getQuantity());
                    cartDto.addCartDetailDto(cartDetailDto);
                }
        );
        return cartDto;
    }


    public Order saveOrderToDB(User user, OrderDto orderDto) {
        Order order = new Order();
        order.setUser(user);

        DeliveryInfo deliveryInfo = orderDto.getDeliveryInfo();
        order.setDeliveryInfo(deliveryInfo);

        order = orderService.save(order); // để tạo order.id => để các order detail có thể link đến

        CartDto cartDto = orderDto.getCart();

        // lưu order details
        // tăng thuộc tính sold của dish và lưu vào DB
        List<CartDetailDto> cartDetailDtos = cartDto.getCartDetails();
        for (CartDetailDto cartDetailDto : cartDetailDtos) {
            OrderDetail orderDetail = new OrderDetail();
            Dish dish = cartDetailDto.getDish();
            int quantity = cartDetailDto.getQuantity();
            orderDetail.setOrder(order);
            orderDetail.setDish(dish);
            orderDetail.setQuantity(quantity);
            orderDetailService.save(orderDetail);

            dish.setSold(dish.getSold() + quantity);
            dishService.save(dish);
        }

        order.setServiceFee(cartDto.getServiceFee());
        order.setShippingFee(cartDto.getShippingFee());
        order.setDiscountAmount(cartDto.getDiscountAmount());
        order.setTotalFee(cartDto.getTotalFee());

        order.setRestaurantNote(orderDto.getNoteRestaurant());
        order.setShippingNote(orderDto.getNoteShipper());
        order.setCoupon(null);

        order = orderService.save(order);
        order.setCreateDate(new Date());
        emptyCartById(orderDto.getCart().getId());
        return orderService.save(order);
    }

    public void emptyCartById(Long cartId){
        Optional<Cart> findCart = findById(cartId);
        if (findCart.isPresent()) {
            cartDetailService.deleteAllByCart(findCart.get());
        }
    };
}
