package at.htlpinkafeld.RMA_backend_java.mock;


import at.htlpinkafeld.RMA_backend_java.dao.EventDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventDaoMock implements EventDao {
    private List<Event> eventList = new ArrayList<>(Arrays.asList(
            new Event(1, "Turnstunde", Date.valueOf("2020-12-01"), Date.valueOf("2020-12-01")),
            new Event(2, "Bundesmeisterschaft", Date.valueOf("2021-01-01"), null),
            new Event(3, "Schulwettbewerb", Date.valueOf("2019-06-20"), Date.valueOf("2019-06-21")),
            new Event(4, "Turnstunde", Date.valueOf("2021-02-02"), Date.valueOf("2021-02-02"))
    ));

    @Override
    public void create(Event event) throws DaoSysException {
        event.setID(getRandomId());
        eventList.add(event);
    }

    @Override
    public Event read(int id) throws DaoSysException {
        for(Event e : eventList) {
            if(e.getID() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void update(Event event) throws DaoSysException {
        for(int i=0; i<eventList.size(); i++) {
            if(eventList.get(i).getID() == event.getID()) {
                eventList.remove(i);
                eventList.add(event);
                return;
            }
        }
        throw new DaoSysException("Event does not exist");
    }

    @Override
    public void delete(Event event) throws DaoSysException {
        if(!existsId(event.getID())) {
            throw new DaoSysException("ID does not exist");
        }
        eventList.remove(event);
    }

    @Override
    public List<Event> list() throws DaoSysException {
        return eventList;
    }

    private int getRandomId() {
        int random;
        while(true) {
            random =  (int)(Math.random()*(10000-1+1)+10000); //random number between 1 and 10,000
            if(!existsId(random)) {
                return random;
            }
        }
    }

    private boolean existsId(int id) {
        for(Event e : eventList) {
            if(e.getID() == id) {
                return true;
            }
        }
        return false;
    }
}
