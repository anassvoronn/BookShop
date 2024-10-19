export class Genre {
    private readonly _value: string;
    private readonly _label: string;

    constructor(value: string, label: string) {
        this._value = value;
        this._label = label;
    }

    get value(): string {
        return this._value;
    }

    get label(): string {
        return this._label;
    }

    static emptyGenre(): Genre {
        return new Genre("", "No Genre");
    }
}
