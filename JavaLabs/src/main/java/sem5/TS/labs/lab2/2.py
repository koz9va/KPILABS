import pyaudio
import wave
from scipy.io.wavfile import read
import numpy as np
import matplotlib.pyplot as plt


p = pyaudio.PyAudio()
for i in range(p.get_device_count()):
    print(i, p.get_device_info_by_index(i)['name'])
chunk = 1024
sample_format = pyaudio.paInt16
channels = 2
rate = 44100
seconds = 5
filename = "/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/resources/output44k.wav"
print("Recording...")

stream = p.open(format=sample_format, channels=channels, rate=rate, frames_per_buffer=chunk, input_device_index=1,
                input=True)
frames = []
for i in range(0, int(rate / chunk * seconds)):
    data = stream.read(chunk)
    frames.append(data)
stream.stop_stream()
stream.close()
p.terminate()
wf = wave.open(filename, 'wb')
wf.setnchannels(channels)
wf.setsampwidth(p.get_sample_size(sample_format))
wf.setframerate(rate)
wf.writeframes(b''.join(frames))
wf.close()
print("Done!")

input_data = read("/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/resources/output8k.wav")
plt.figure("8K")
audio = input_data[1]
t = np.linspace(0, 5, len(audio))
plt.plot(t, audio)
plt.ylabel("Amplitude")
plt.xlabel("Time")
plt.title("Sample Wav")
plt.grid(True)
plt.figure("44.1K")
input_data = read("/Users/dmytro/IdeaProjects/KPILABS/JavaLabs/src/main/resources/output44k.wav")
audio = input_data[1]
t = np.linspace(0, 5, len(audio))
plt.plot(t, audio)
plt.ylabel("Amplitude")
plt.xlabel("Time, s")
plt.title("Sample Wav")
plt.grid(True)
plt.show()
