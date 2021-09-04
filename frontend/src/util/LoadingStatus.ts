enum LoadingStatus {
    loaded = "loaded",
    loading = "loading",
    error = "error",
}

export default LoadingStatus;

type switchStatusArgs<Type> = {
    onLoaded: () => Type,
    onLoading: () => Type,
    onError: () => Type,
}

export function switchStatus<ReturnType>(
    status: LoadingStatus,
    {
        onLoaded,
        onLoading,
        onError,
    }: switchStatusArgs<ReturnType>,
): ReturnType {
    switch (status) {
        case LoadingStatus.loaded:
            return onLoaded();
        case LoadingStatus.loading:
            return onLoading();
        case LoadingStatus.error:
            return onError();
    }
}
