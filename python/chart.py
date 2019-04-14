import matplotlib.pyplot as plt

min_sup = [35, 30, 25, 20, 15]

runtime_prepost_plus = [0.99, 1.50, 2.50, 4.5, 6]
runtime_prepost = [2, 3.5, 4.50, 6, 10]
runtime_fp = [0.1, 3.6, 4.60, 6.1, 10.1]

memory_prepost_plus = [5, 4, 3, 2, 1]
memory_prepost = [5.5, 10, 15, 23, 83]
memory_fp = [5.5, 4.5, 3.5, 2.5, 1.5]

plt.subplot(2,1,1)

plt.plot(min_sup, runtime_prepost_plus, label = 'prepost+', marker="o")
plt.plot(min_sup, runtime_prepost, label = 'prepost', marker="v")
plt.plot(min_sup, runtime_fp, label = 'fp_growth', marker="s")
plt.xlim(35, 15)

plt.title('Testing on chess.dat dataset')
plt.xlabel('Minimum Support (%)')
plt.ylabel('runtime (s)')
plt.legend(loc='upper left')

plt.subplot(2,1,2)

plt.plot(min_sup, memory_prepost_plus, label = 'prepost+', marker="o")
plt.plot(min_sup, memory_prepost, label = 'prepost', marker="v")
plt.plot(min_sup, memory_fp, label = 'fp_growth', marker="s")
plt.xlim(35, 15)

plt.xlabel('Minimum Support (%)')
plt.ylabel('MemoryUsage (M)')
plt.legend(loc='upper left')

plt.show()
