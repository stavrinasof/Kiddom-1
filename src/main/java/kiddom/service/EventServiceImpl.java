package kiddom.service;

import kiddom.model.ProgramEntity;
import kiddom.model.ProviderEntity;
import kiddom.model.SingleEventEntity;
import kiddom.model.UserEntity;
import kiddom.repository.ActivityRepository;
import kiddom.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by Arianna on 1/7/2017.
 */
@Service("eventService")
public class EventServiceImpl implements  EventService {

    @Qualifier("programRepository")
    @Autowired
    private ProgramRepository programRepository;

    @Qualifier("activityRepository")
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public SingleEventEntity findSingleEvent(SingleEventEntity singleEventEntity) {
        return activityRepository.findSingleEventById(1/*singleEventEntity.getId()*/);
    }

    /*@Override
    public Page<SingleEventEntity> getAllEvents(Pageable pageable){
       // Page<SingleEventEntity> eventList =
        return activityRepository.findAll(pageable);
    }*/

    @Override
    public void saveActivity(UserEntity user, ProviderEntity provider, SingleEventEntity event, HashSet<ProgramEntity> program)
    {
        /*
        String photos = null;
        if (request.getParameter("image1") != null) {
			photos += request.getParameter("image1");
			photos += "\n";
		}
        if (request.getParameter("image2") != null) {
			photos += request.getParameter("image2");
			photos += "\n";
		}
        if (request.getParameter("image3") != null) {
			photos += request.getParameter("image3");
			photos += "\n";
		}
        if (request.getParameter("image4") != null) {
			photos += request.getParameter("image4");
			photos += "\n";
		}
        if (request.getParameter("image5") != null) {
			photos += request.getParameter("image5");
			photos += "\n";
		}
		event.setPhotos(photos);*/
        event.setProviders(provider);
        System.out.println("Event id is " + event.getId());
        //event.setProgram(program);
        System.out.println("Event by " + provider.getPk().getUser().getUsername());
        activityRepository.save(event);
        for (ProgramEntity daily_program : program) {
            daily_program.setEvent(event);
            programRepository.save(daily_program);
        }
        event.setProgram(program);
        activityRepository.saveAndFlush(event);
        System.out.println("Done.");
    }
}