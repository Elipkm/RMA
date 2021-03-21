export class Event{

    constructor(private name: string, private startDate: Date, 
        private endDate?: Date, private id?: number){

    }

    get ID(){
        return this.id;
    }

    set ID(id: number){
        this.id = id;
    }

    get Name(){
        return this.name;
    }

    set Name(name: string){
        this.name = name;
    }

    get StartDate(){
        return this.startDate;
    }

    set StartDate(startDate: Date){
        this.startDate = startDate;
    }

    get EndDate(){
        return this.endDate;
    }

    set EndDate(endDate: Date){
        this.endDate = endDate;
    }

    public static eventOfObject(object: any): Event{
        return new Event(object.name, object.startDate, object.endDate, object.id);
    }

}