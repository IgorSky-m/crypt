package it.academy.cryptorest.rest;

import it.academy.cryptorest.pojo.UserWallet;
import it.academy.cryptorest.pojo.Wallet;
import it.academy.cryptorest.pojo.WalletAmount;
import it.academy.cryptorest.service.CustomUserService;
import it.academy.cryptorest.service.CustomUserWalletService;
import it.academy.cryptorest.service.WalletService;
import it.academy.cryptorest.utill.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@Getter
@Setter
public class WalletController {

    @Autowired
    private WalletService service;

    @Autowired
    private CustomUserWalletService userWalletService;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/wallets")
    public CollectionModel<EntityModel<Wallet>> all(){
        return CollectionModel.of(
            service.findAll().stream()
            .map(service::toEntityModel)
            .collect(Collectors.toList())
        );
    }


    @GetMapping("/wallets/{id}")
    public EntityModel<Wallet> one(
            @PathVariable String id
    ){

        Wallet wallet = service.findById(id);

        return service.toEntityModel(wallet);
    }


    @PutMapping("/wallets/{id}/update")
    public ResponseEntity<EntityModel<Wallet>> update(
            @PathVariable String id,
            @RequestBody Wallet wallet
    ){
        Wallet savedWallet = service.update(wallet,id);
        return ResponseEntity.ok(service.toEntityModel(savedWallet));
    }

    @DeleteMapping("wallets/{id}/delete")
    public ResponseEntity<EntityModel<Wallet>> delete(
            @PathVariable String id
    ){

        Wallet deletedWallet = service.findById(id);
        service.deleteById(id);
        return ResponseEntity.ok(service.toEntityModel(deletedWallet));

    }

    @GetMapping("users/{userId}/wallets")
    public ResponseEntity<?> userWallets(
            @PathVariable String userId
    ){
        if ("undefied".equals(userId)) return ResponseEntity.badRequest().build();
        List<WalletAmount> result = service.findAllByUserId(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/generate/wallet")
    public ResponseEntity<Wallet> generate(
    ){
        Wallet wallet = service.generateWallet();

        return ResponseEntity.ok(wallet);
    }




    @PostMapping("/wallets")
    public ResponseEntity<Wallet> post(
            HttpServletRequest request , HttpServletResponse response,
            @RequestBody Wallet wallet
    ){
        service.save(wallet);
        String userName = jwtUtil.getUsernameFromHttpRequest(request);
        String userId = customUserService.findUserByName(userName).getId();

        UserWallet userWallet = UserWallet.builder()
                .userId(userId)
                .walletId(wallet.getId())
                .build();

        userWalletService.save(userWallet);


        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/wallets/{id}/amount")
    public Long walletAmount(
            @PathVariable String id
     ){
        return null;
    }

}
