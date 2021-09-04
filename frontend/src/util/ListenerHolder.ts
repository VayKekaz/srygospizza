import IdGenerator from "./IdGenerator";

type Listener<T> = (arg: T) => void

export default class ListenerHolder<ListenerSuppliedValue> {

    private listeners: { [id: number]: Listener<ListenerSuppliedValue> } = {};

    /**
     * Generates ids for listeners.
     */
    private idGenerator = new IdGenerator();

    /**
     * Function used to receive fresh value for listeners inside `notify()` method.
     */
    private readonly getListenerSuppliedValue: () => ListenerSuppliedValue;

    constructor(getListenerSuppliedValue: () => ListenerSuppliedValue) {
        this.getListenerSuppliedValue = getListenerSuppliedValue;
    }

    /**
     * Registers listener, assigns id to it, returns its id.
     * @return id of listener added.
     */
    public add(listener: Listener<ListenerSuppliedValue>): number {
        const listenerId = this.idGenerator.getId();
        this.listeners[listenerId] = listener;
        return listenerId;
    }

    /**
     * Removes listener with given id.
     * @param id id of a listener to be removed.
     * Should be always value gained from `add(listener)` method.
     */
    public remove(id: number) {
        delete this.listeners[id];
    }

    /**
     * Notifies all the listeners and passes value received from
     * `getListenerSuppliedValue()` to all of them.
     */
    public notify() {
        const valueToPass = this.getListenerSuppliedValue();
        Object.values(this.listeners)
            .forEach(callback => callback(valueToPass));
    }
}