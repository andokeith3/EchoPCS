# qrcode tem que ser instalado com "pip install qrcode" e pode precisar de PIL (https://pypi.org/project/qrcode/)
# google TTS: "pip install --upgrade google-cloud-texttospeech" (https://cloud.google.com/text-to-speech/docs/)
import qrcode
import codecs
from google.cloud import texttospeech as tts
# Necessario para a autenticacao
from os import environ

# Inserindo credenciais do google (OBS: EDITAR CAMINHO!!!) e informações que serão utilizadas na conexao SQL
environ["GOOGLE_APPLICATION_CREDENTIALS"] = "keys.json"
# Não é adequado que as credenciais sejam hard-coded, todavia, a implementação de medidas de segurança adequadas é
# bastante complicada.
Info_De_Conexao = {
    "host": "ip_placeholder",
    "user": "user_placeholder",
    "password": "pass_placeholder",
    "database": "db_placeholder"
}
print('Credenciais definidas.')


# Funcoes que geram binario a partir de arquivo e vice versa
def arquivo_para_binario(filename):
    with open(filename, 'rb') as file:
        return file.read()


def binario_para_arquivo(binario, output):
    with open(output, 'wb') as file:
        file.write(binario)


# Gera lista de strings a partir de arquivo
def strings_de_txt(arquivo):
    with codecs.open(arquivo, encoding='utf-8') as f:
        textao = f.read()
    return textao.split("\t")


# Gera QR Code a partir de string e salva
# Pode-se configurar a estética dos arquivos produzidos
def gera_qr(texto, output):
    imagem = qrcode.make(texto)
    imagem.save(output)


# Gera arquivo mp3 a partir de string e salva
def gera_voz(texto, lingua="pt-BR", genero=tts.SsmlVoiceGender.NEUTRAL,
             formato=tts.AudioEncoding.MP3, output="output.mp3", modo="wb"):

    # Cria instancia de cliente
    cliente = tts.TextToSpeechClient()

    # Define o texto a ser sintetizado
    input_de_texto = tts.SynthesisInput(text=texto)

    # Constroi o request de voz e seleciona a linguagem, alem do genero
    voz = tts.VoiceSelectionParams(
        language_code=lingua, ssml_gender=genero
    )

    # Seleciona o formato de audio
    audio_config = tts.AudioConfig(
        audio_encoding=formato
    )

    # Executa o request de voz com os parametros selecionados
    resposta = cliente.synthesize_speech(
        input=input_de_texto, voice=voz, audio_config=audio_config
    )

    # resposta.audio_content tem formato binario
    with open(output, modo) as out:
        # Salvando a resposta para o output
        out.write(resposta.audio_content)
        out.close()


# Cria mp3 a partir de arquivo .txt
def gera_mp3zao(arquivo, out):
    for texto in strings_de_txt(arquivo):
        gera_voz(texto, modo="ab+", output=out)
