from algorithm import load_csv
from algorithm import Autoencoder
import random
from algorithm import font_3
from algorithm import to_bin_array
import matplotlib.pyplot as plt
import numpy as np
neurons_per_layer = [20, 10, 2,10,20]

font_keys = ["`", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "[", "|","]", "~", "DEL"]

font_3 = np.array([
   [0x04, 0x04, 0x02, 0x00, 0x00, 0x00, 0x00],   # 0x60, `
   [0x00, 0x0e, 0x01, 0x0d, 0x13, 0x13, 0x0d],   # 0x61, a
   [0x10, 0x10, 0x10, 0x1c, 0x12, 0x12, 0x1c],   # 0x62, b
   [0x00, 0x00, 0x00, 0x0e, 0x10, 0x10, 0x0e],   # 0x63, c
   [0x01, 0x01, 0x01, 0x07, 0x09, 0x09, 0x07],   # 0x64, d
   [0x00, 0x00, 0x0e, 0x11, 0x1f, 0x10, 0x0f],   # 0x65, e
   [0x06, 0x09, 0x08, 0x1c, 0x08, 0x08, 0x08],   # 0x66, f
   [0x0e, 0x11, 0x13, 0x0d, 0x01, 0x01, 0x0e],   # 0x67, g
   [0x10, 0x10, 0x10, 0x16, 0x19, 0x11, 0x11],   # 0x68, h
   [0x00, 0x04, 0x00, 0x0c, 0x04, 0x04, 0x0e],   # 0x69, i
   [0x02, 0x00, 0x06, 0x02, 0x02, 0x12, 0x0c],   # 0x6a, j
   [0x10, 0x10, 0x12, 0x14, 0x18, 0x14, 0x12],   # 0x6b, k
   [0x0c, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04],   # 0x6c, l
   [0x00, 0x00, 0x0a, 0x15, 0x15, 0x11, 0x11],   # 0x6d, m
   [0x00, 0x00, 0x16, 0x19, 0x11, 0x11, 0x11],   # 0x6e, n
   [0x00, 0x00, 0x0e, 0x11, 0x11, 0x11, 0x0e],   # 0x6f, o
   [0x00, 0x1c, 0x12, 0x12, 0x1c, 0x10, 0x10],   # 0x70, p
   [0x00, 0x07, 0x09, 0x09, 0x07, 0x01, 0x01],   # 0x71, q
   [0x00, 0x00, 0x16, 0x19, 0x10, 0x10, 0x10],   # 0x72, r
   [0x00, 0x00, 0x0f, 0x10, 0x0e, 0x01, 0x1e],   # 0x73, s
   [0x08, 0x08, 0x1c, 0x08, 0x08, 0x09, 0x06],   # 0x74, t
   [0x00, 0x00, 0x11, 0x11, 0x11, 0x13, 0x0d],   # 0x75, u
   [0x00, 0x00, 0x11, 0x11, 0x11, 0x0a, 0x04],   # 0x76, v
   [0x00, 0x00, 0x11, 0x11, 0x15, 0x15, 0x0a],   # 0x77, w
   [0x00, 0x00, 0x11, 0x0a, 0x04, 0x0a, 0x11],   # 0x78, x
   [0x00, 0x11, 0x11, 0x0f, 0x01, 0x11, 0x0e],   # 0x79, y
   [0x00, 0x00, 0x1f, 0x02, 0x04, 0x08, 0x1f],   # 0x7a, z
   [0x06, 0x08, 0x08, 0x10, 0x08, 0x08, 0x06],   # 0x7b, [
   [0x04, 0x04, 0x04, 0x00, 0x04, 0x04, 0x04],   # 0x7c, |
   [0x0c, 0x02, 0x02, 0x01, 0x02, 0x02, 0x0c],   # 0x7d, ]
   [0x08, 0x15, 0x02, 0x00, 0x00, 0x00, 0x00],   # 0x7e, ~
   [0x1f, 0x1f, 0x1f, 0x1f, 0x1f, 0x1f, 0x1f]   # 0x7f, DEL
   ])
all_inputs = []   
def to_bin_array(encoded_caracter):
    bin_array = np.zeros((7, 5), dtype=int)
    for row in range(0, 7):
        current_row = encoded_caracter[row]
        for col in range(0, 5):
            bin_array[row][4-col] = current_row & 1
            current_row >>= 1
    return bin_array.flatten()
for font in font_3:
    all_inputs.append(to_bin_array(font))


autoencoder = Autoencoder(neurons_per_layer, all_inputs, all_inputs, 1)
autoencoder.train()

results = np.empty([len(all_inputs),2])
for inp in all_inputs:
    np.append(results,autoencoder.encode(inp))

print(results)
plt.figure(figsize=(6, 6))
print(results[:,0])
print(results[:,1])
plt.scatter(results[:,0], results[:,1], c=range(len(all_inputs)), cmap='viridis')
for i in range(32):
    plt.annotate(font_keys[i], (results[i,0], results[i,1]))
plt.colorbar()
plt.show() 

