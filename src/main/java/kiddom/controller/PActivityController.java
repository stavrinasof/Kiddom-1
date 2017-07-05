package kiddom.controller;

/**
 * Created by eleni on 04-Jul-17.
 */

import kiddom.authentication.IAuthenticationFacade;
import kiddom.model.*;
import kiddom.service.EventService;
import kiddom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;

@Controller
public class PActivityController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value="/activityProvider/{eventID}", method = RequestMethod.GET)
    public ModelAndView activity_show(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @PathVariable String eventID){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is"+authentication.getName());
        if(!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UserEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }
        System.out.println("Phra " + eventID);
        Integer eventID_ = Integer.parseInt(eventID);
        System.out.println("Diavasa " + eventID_);
        SingleEventEntity event = eventService.findSingleEventById(eventID_);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        System.out.println(dtf.format(localDate));
        String currDate1 = dtf.format(localDate).toString();
        if (event.getProgram() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            for (ProgramEntity program : event.getProgram()) {
                try {
                    Date currDate = sdf.parse(currDate1);
                    Date eventDate = sdf.parse(program.getDate());
                    if (eventDate.after(currDate)) {
                    //    currEvent
                        modelAndView.addObject("eventStatus", 1);
                    }
                    else {
                    //    pastEvent
                        modelAndView.addObject("eventStatus", 2);
                    }
                }
                catch (ParseException e) {
                }
                break;
            }
        }
        else {
            modelAndView.addObject("eventStatus", 0);
        }

        modelAndView.addObject("event", event);
        modelAndView.setViewName("/activityProvider");
        return modelAndView;
    }

    @RequestMapping(value="/edit_event/{eventID}", method = RequestMethod.POST)
    public ModelAndView edit_event(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("single_event") @Valid SingleEventEntity event, @PathVariable String eventID)
    {
        System.out.println("Provider name: " + provider.getName());
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is " + authentication.getName());
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            Integer eventID_ = Integer.parseInt(eventID);
            SingleEventEntity eventEdit = eventService.findSingleEventById(eventID_);
            System.out.println("Kanw update sto " + eventID_);
            eventService.updateSingleEvent(provider, event, eventEdit);
            modelAndView.addObject("event", eventEdit);
            modelAndView.addObject("provider", provider);
            modelAndView.addObject("user", user);
        }
        modelAndView.setViewName("activityProvider");
        return modelAndView;
    }

    @RequestMapping(value="/event_cancelation/{eventID}", method = RequestMethod.POST)
    public  ModelAndView event_cancelation(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("single_event") @Valid SingleEventEntity event, @PathVariable String eventID) {
        System.out.println("Provider name: " + provider.getName());
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is " + authentication.getName());
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            Integer eventID_ = Integer.parseInt(eventID);
            SingleEventEntity eventEdit = eventService.findSingleEventById(eventID_);
            System.out.println("Kanw update sto " + eventID_);
            eventService.cancelSingleEvent(provider, eventEdit);
            modelAndView.addObject("event", eventEdit);
            modelAndView.addObject("provider", provider);
            modelAndView.addObject("user", user);
        }
        modelAndView.setViewName("activityProvider");
        return modelAndView;
    }

    //@RequestMapping(value="/slot_cancelation/{eventID}/{slotID}", method = RequestMethod.POST)
    //public ModelAndView
}
