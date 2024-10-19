export class Author {
    private readonly _id: number;
    private readonly _name: string;
    private readonly _gender: string;
    private readonly _birthDate: Date;
    private readonly _deathDate: Date;
    private readonly _country: string;
    private readonly _age: number;

    constructor(id: number, name: string, gender: string, birthDate: Date, deathDate: Date, country: string, age: number) {
        this._id = id;
        this._name = name;
        this._gender = gender;
        this._birthDate = birthDate;
        this._deathDate = deathDate;
        this._country = country;
        this._age = age;
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

    get name(): string {
        return this._name;
    }

    get gender(): string {
        return this._gender;
    }

    get birthDate(): Date {
        return this._birthDate;
    }

    get deathDate(): Date {
            return this._deathDate;
        }

    get country(): string {
        return this._country;
    }

    get age(): number {
        return this._age;
    }

    static emptyAuthor(): Author {
        return new Author(0, "No Author", "Unknown", new Date(), new Date(), "Unknown", 0);
    }
}
