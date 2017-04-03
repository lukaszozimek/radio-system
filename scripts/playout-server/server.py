#!flask/bin/python
from flask import Flask, jsonify
from deck import Deck

app = Flask(__name__)
deck = Deck('FOO', -1, 44100)

@app.route('/load/old/<string:cd_set>/<string:media_id>', methods=['GET'])
def loadOld(cd_set, media_id):
    fileName = '/Users/gk/RR/{0}/{1}'.format(cd_set, media_id)
    deck.load(fileName=fileName)
    return "Load" 

@app.route('/load/new/<string:network>/<string:library>/<string:item>', methods=['GET'])
def loadNew(network, library, item):
    deck.load(networkId=network, libraryPrefix=library, mediaId=item)
    return "Load" 

@app.route('/play')
def play():
    deck.play()
    return "Play"

@app.route('/pause')
def pause():
    deck.pause()
    return "Pause"

@app.route('/stop')
def stop():
    deck.stop()
    return "Stop"

@app.route('/unload')
def unload():
    deck.unload()
    return "Unload"

if __name__ == '__main__':
    app.run(debug=True)