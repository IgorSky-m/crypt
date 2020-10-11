package it.academy.cryptorest.aspect;

import it.academy.cryptorest.pojo.CustomUser;
import it.academy.cryptorest.repository.CustomUserRepository;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Setter
@Getter
public class CustomUserAspect {

    private boolean checkStatus;


    @Value("${check.message.if.exist}")
    private String str;

    @Autowired
    private CustomUserRepository customUserRepository;


    @Pointcut("@annotation(UserParamCheck) && args(customUser,..)")
    public void methodPoint(CustomUser customUser){
    }

    @Pointcut("@annotation(AutoControlParam) && args(param,..)")
    public void paramPoint(boolean param){
    }



    @Before("methodPoint(customUser)")
    public CustomUser checkUser(CustomUser customUser) {
        checkStatus = true;
        if (customUserRepository.findByUserName(customUser.getUserName()) != null) {
            customUser.setUserName(str);
            checkStatus = false;
        }
        if(customUserRepository.findByEmailAddress(customUser.getEmailAddress()) != null) {
            customUser.setEmailAddress(str);
            if (checkStatus) checkStatus = false;
        }

        if (customUserRepository.findByMobile(customUser.getMobile()) != null) {
            customUser.setMobile(str);
            if (checkStatus) checkStatus = false;
        }

        return customUser;
    }



}
