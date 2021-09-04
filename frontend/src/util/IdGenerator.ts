export default class IdGenerator {
    private id = 0;

    getId(): number {
        return this.id++;
    }
}

export const globalIdGenerator = new IdGenerator();
