export class Event{

    constructor(private _id: number, private _name: string,
                private _startDate: Date, private _endDate: Date){
    }

    get id(){
        return this._id;
    }

    set id(id: number){
        this._id = id;
    }

    get name(){
        return this._name;
    }

    set name(name: string){
        this._name = name;
    }

    get startDate(){
        return this._startDate;
    }

    set startDate(startDate: Date){
        this._startDate = startDate;
    }

    get endDate(){
        return this._endDate;
    }

    set endDate(endDate: Date){
        this._endDate = endDate;
    }

    public static eventOfObject(object: any): Event{
        return new Event(object.id, object.name, object.startDate, object.endDate);
    }

}