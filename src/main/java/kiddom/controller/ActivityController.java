package kiddom.controller;

import kiddom.model.ProviderEntity;
import kiddom.model.SingleEventEntity;
import kiddom.model.UserEntity;
import kiddom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Arianna on 6/6/2017.
 */
@Controller
public class ActivityController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/activity_reg", method = RequestMethod.GET)
    public ModelAndView activity_register(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user){
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userExists=userService.findByUsername(user.getUsername());
		/*if(userExists == null){
			System.out.println("No one is logged in");
			modelAndView.setViewName("/error_page");
			return modelAndView;
		}
		else if(userExists.getType()==1) {
			System.out.println("The one registered is not a provider");
			modelAndView.setViewName("/provider_req");
			return modelAndView;
		}*/
        modelAndView.addObject("user", user);
        modelAndView.addObject("provider",provider);
        modelAndView.setViewName("/activity_reg");
        return modelAndView;
    }



    @RequestMapping(value="/activity_reg", method = RequestMethod.POST)
    public ModelAndView activity_creation(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("single_event") @Valid SingleEventEntity event, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userExists=userService.findByUsername(user.getUsername());
		/*if(userExists == null){
			modelAndView.addObject("user",user);
			modelAndView.setViewName("/error_page");
			return modelAndView;
		}
		else if(userExists.getType()==1){
			modelAndView.addObject("user",user);
			modelAndView.addObject("provider",provider);
			modelAndView.setViewName("/provider_req");
			return modelAndView;
		}*/
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("user",user);
            modelAndView.setViewName("/error_page");
            return modelAndView;
        } else {
            userService.saveActivity(user, provider, event);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", user);
            modelAndView.addObject("provider", provider);
            modelAndView.addObject("single_event", event);
        }
        modelAndView.setViewName("redirect:/activity");
        return modelAndView;
    }

}