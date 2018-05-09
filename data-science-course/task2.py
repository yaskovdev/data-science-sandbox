import numpy as np
import matplotlib.pyplot as plt

x = np.arange(1.0, 15.0, 0.01)
f = np.sin(x / 5.0) * np.exp(x / 10.0) + 5 * np.exp(-x / 2.0)

plt.plot(x, f)
