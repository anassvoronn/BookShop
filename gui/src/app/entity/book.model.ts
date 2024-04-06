export class Book {
    private readonly _id: number;
    private readonly _title: string;
    private readonly _publishingYear: number;
    private readonly _genre: string;

    constructor(id: number, title: string, publishingYear: number, genre: string){
        this._id = id;
        this._title = title;
        this._publishingYear = publishingYear;
        this._genre = genre;
    }

    toJsonString(): string {
        let json = JSON.stringify(this);
        Object.keys(this).filter(key => key[0] === "_").forEach(key => {
            json = json.replace(key, key.substring(1));
        });

        return json;
    }

     get id(): number {
         return this._id;
     }

     get title(): string {
         return this._title;
     }

     get publishingYear(): number {
         return this._publishingYear;
     }

     get genre(): string {
         return this._genre;
     }
}
