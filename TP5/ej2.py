import tensorflow as tf
from keras.datasets import mnist
from keras import metrics
from keras.layers import Input, Dense, Lambda, Reshape
from keras.models import Model
from keras import backend as K
from keras import metrics
from keras.datasets import mnist
import numpy as np

from PIL import Image
from tensorflow.python.framework.ops import disable_eager_execution
disable_eager_execution()


batch_size = 100

original_dim = 30*30

latent_dim = 6
intermediate_dim = 270
epochs = 100
epsilon_std = 1

def sampling(args: tuple):
    # we grab the variables from the tuple
    z_mean, z_log_var = args
    print(z_mean)
    print(z_log_var)
    epsilon = K.random_normal(shape=(K.shape(z_mean)[0], latent_dim), mean=0.,
                              stddev=epsilon_std)
    return z_mean + K.exp(z_log_var / 2) * epsilon  # h(z)


# input to our encoder
x = Input(shape=(original_dim,), name="input")
# intermediate layer
h = Dense(intermediate_dim, activation='relu', name="encoding")(x)
# defining the mean of the latent space
z_mean = Dense(latent_dim, name="mean")(h)
# defining the log variance of the latent space
z_log_var = Dense(latent_dim, name="log-variance")(h)
# note that "output_shape" isn't necessary with the TensorFlow backend
z = Lambda(sampling, output_shape=(latent_dim,))([z_mean, z_log_var])
# defining the encoder as a keras model
encoder = Model(x, [z_mean, z_log_var, z], name="encoder")
# print out summary of what we just did
encoder.summary()


# Input to the decoder
input_decoder = Input(shape=(latent_dim,), name="decoder_input")
# taking the latent space to intermediate dimension
decoder_h = Dense(intermediate_dim, activation='relu', name="decoder_h")(input_decoder)
# getting the mean from the original dimension
x_decoded = Dense(original_dim, activation='sigmoid', name="flat_decoded")(decoder_h)
# defining the decoder as a keras model
decoder = Model(input_decoder, x_decoded, name="decoder")
decoder.summary()



# grab the output. Recall, that we need to grab the 3rd element our sampling z
output_combined = decoder(encoder(x)[2])
# link the input and the overall output
vae = Model(x, output_combined)
# print out what the overall model looks like
vae.summary()



def vae_loss(x: tf.Tensor, x_decoded_mean: tf.Tensor):
  # Aca se computa la cross entropy entre los "labels" x que son los valores 0/1 de los pixeles, y lo que salió al final del Decoder.
  xent_loss = original_dim * metrics.binary_crossentropy(x, x_decoded_mean) # x-^X
  kl_loss = - 0.5 * K.sum(1 + z_log_var - K.square(z_mean) - K.exp(z_log_var), axis=-1)
  vae_loss = K.mean(xent_loss + kl_loss)
  return vae_loss

vae.compile( loss=vae_loss)
vae.summary()

from os import walk


images = []

filenames = next(walk("data"), (None, None, []))[2]

labels = []
i=0
for filename in filenames:


    im = Image.open("data/" + filename)
    p = np.array( im.resize((30, 30), Image.ANTIALIAS) )
    images.append(p.reshape(30*30,))
    labels.append(i)
    i+=1


from sklearn.model_selection import train_test_split
xtrain, xtest, ytrain, ytest = train_test_split(images, labels, test_size=0.2,
                                                random_state=42)


x_train = np.array(xtrain)
x_test = np.array(xtest)
y_train = np.array(ytrain)
y_test = np.array(ytest)

print("data de y:",y_train)
print("data de x:",x_train)




vae.fit(x_train, x_train,
        shuffle=True,
        epochs=epochs,
        batch_size=batch_size)


from scipy.stats import norm

import matplotlib.pyplot as plt



n=1
#generate new samples using variational autoencoder
z_sample = np.random.normal(size=(n, latent_dim))
x_decoded = decoder.predict(z_sample)
x_decoded = x_decoded.reshape((n, 30, 30))





print(x_decoded)

plt.tick_params(left = False, right = False , labelleft = False ,
                labelbottom = False, bottom = False)
plt.imshow(x_decoded[0])

plt.show()