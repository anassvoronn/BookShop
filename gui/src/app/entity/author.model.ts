export class Author {
    private readonly _id: number;
    private readonly _name: string;
    private readonly _gender: string;
    private readonly _birthDate: Date;
    private readonly _deathDate: Date;
    private readonly _country: string;

    constructor(id: number, name: string, gender: string, birthDate: Date, deathDate: Date, country: string) {
        this._id = id;
        this._name = name;
        this._gender = gender;
        this._birthDate = birthDate;
        this._deathDate = deathDate;
        this._country = country;
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
}
