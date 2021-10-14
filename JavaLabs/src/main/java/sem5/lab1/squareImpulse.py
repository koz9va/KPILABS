import numpy as np


def squareImpulse(amp, times, shape, at):
    if not isinstance(at, list):
        print("Non list type at signals list, critical error")
        return

    def rect(T):
        return lambda t: (-T / 2 <= t) & (t < T / 2)

    def pulse_train(t, at, shape):
        return np.sum(shape(t - at[:, np.newaxis]), axis=0)

    for i in range(len(at)):
        if at[i] + shape > times[-1] or at[i] - shape < times[0]:
            print("signal away from border, critical error, returning wrong value")
            return
    return amp * pulse_train(
        times,
        at=np.array(at),
        shape=rect(shape)
    )
