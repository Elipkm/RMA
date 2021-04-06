package at.htlpinkafeld.restful.mock;

import at.htlpinkafeld.restful.dao.Crud;
import at.htlpinkafeld.restful.exception.DaoNotFoundException;
import at.htlpinkafeld.restful.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.Identifiable;
import java.util.List;

public abstract class GenericMock<T extends Identifiable> implements Crud<T> {

    private List<T> mockData;

    protected GenericMock(List<T> mockData) {
        this.mockData = mockData;
    }

    @Override
    public void create(T t) throws DaoSysException, DaoResourceAlreadyExistsException {
        if(t.getID() >= 0){
            throw new IllegalArgumentException("Id must not be set");
        }
        if(isUnique(t)) {
            t.setID(getRandomId());
            mockData.add(t);
        }else{
            throw new DaoResourceAlreadyExistsException("Entity already exists");
        }

    }

    @Override
    public T read(int id) throws DaoSysException, DaoNotFoundException {
        for(T identifiable : mockData) {
            if(identifiable.getID() == id) {
                return identifiable;
            }
        }
        throw new DaoNotFoundException("No Entity with matching ID found");
    }

    @Override
    public void update(T t) throws DaoSysException, DaoNotFoundException {
        for(int i = 0; i < mockData.size(); i++) {
            if(mockData.get(i).getID() == t.getID()) {
                mockData.set(i,t);
                return;
            }
        }
        throw new DaoNotFoundException("No Entity with matching ID found");
    }

    @Override
    public void delete(T t) throws DaoSysException, DaoNotFoundException {
        if(existsId(t.getID())) {
            mockData.remove(t);
        }else {
            throw new DaoNotFoundException("Event does not exist");
        }
    }

    @Override
    public List<T> list(){
        return this.mockData;
    }

    private boolean existsId(int id){
        for(Identifiable identifiable : mockData){
            if(id == identifiable.getID()){
                return true;
            }
        }
        return false;
    }
    private boolean isUnique(Identifiable identifiable){
        for(Identifiable i : mockData){
            if(identifiable.getID() == i.getID()){
                return false;
            }
        }
        return true;
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
}
