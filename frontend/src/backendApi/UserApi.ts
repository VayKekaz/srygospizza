import ListenerHolder from "../util/ListenerHolder";

class UserApi {

    private loggedIn: boolean = false;
    public isLoggedIn = () => this.loggedIn;

    public getUsername(): string {
        return "NULL";
    }

    public getAvatar(): string {
        return "NULL";
    }

    public readonly listeners = new ListenerHolder<boolean>(this.isLoggedIn)
}

export default new UserApi();