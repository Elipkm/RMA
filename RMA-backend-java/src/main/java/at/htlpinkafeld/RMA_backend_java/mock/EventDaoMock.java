package at.htlpinkafeld.RMA_backend_java.mock;


import at.htlpinkafeld.RMA_backend_java.dao.EventDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import java.sql.Date;
import java.util.*;

public class EventDaoMock implements EventDao {
    private final List<Event> eventList = new ArrayList<>(Arrays.asList(
            new Event(1, "Turnstunde", Date.valueOf("2020-12-01"), Date.valueOf("2020-12-01")),
            new Event(2, "Bundesmeisterschaft", Date.valueOf("2021-01-01"), null),
            new Event(3, "Schulwettbewerb", Date.valueOf("2019-06-20"), Date.valueOf("2019-06-21")),
            new Event(4, "Turnstunde", Date.valueOf("2021-02-02"), Date.valueOf("2021-02-02"))
    ));

    private final Map<String,List<Event>> orMapping = new HashMap<>();
    public EventDaoMock(){
        orMapping.put("elias",  new ArrayList<>(Arrays.asList(eventList.get(0))));
        orMapping.put("daniel", new ArrayList<>(Arrays.asList(eventList.get(1))));
        orMapping.put("markus", new ArrayList<>(Arrays.asList(eventList.get(2))));
        orMapping.put("lukas",  new ArrayList<>(Arrays.asList(eventList.get(3))));
    }

    @Override
    public void create(User user, Event event) {
        List<Event> userEvents = orMapping.get(user.getUsername());

        event.setID(getRandomId(userEvents));
        userEvents.add(event);

        orMapping.put(user.getUsername(),userEvents);
    }

    @Override
    public Event read(User user, int id) throws DaoNotFoundException {
        List<Event> userEvents = orMapping.get(user.getUsername());
        for(Event e : userEvents) {
            if(e.getID() == id) {
                return e;
            }
        }
        throw new DaoNotFoundException("Event does not exist");
    }

    @Override
    public void update(User user, Event event) throws DaoNotFoundException {
        List<Event> userEvents = orMapping.get(user.getUsername());
        for(int i=0; i < userEvents.size(); i++) {
            if(userEvents.get(i).getID() == event.getID()) {
                userEvents.remove(i);
                userEvents.add(event);
                orMapping.put(user.getUsername(),userEvents);
                return;
            }
        }
        throw new DaoNotFoundException("Event does not exist");
    }

    @Override
    public void delete(User user, Event event) throws DaoNotFoundException {
        List<Event> userEvents = orMapping.get(user.getUsername());

        if(!existsId(userEvents, event.getID())) {
            throw new DaoNotFoundException("Event does not exist");
        }
        userEvents.remove(event);
        orMapping.put(user.getUsername(), userEvents);
    }

    private int getRandomId(List<Event> eventList) {
        int random;
        while(true) {
            random =  (int)(Math.random()*(10000-1+1)+10000); //random number between 1 and 10,000
            if(!existsId(eventList,random)) {
                return random;
            }
        }
    }

    private boolean existsId(List<Event> eventList, int id) {
        for(Event e : eventList) {
            if(e.getID() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Event> list(User user) {
        return orMapping.get(user.getUsername());
    }
}
