import geradores
import mysql.connector

# Selecionando um dos fragmentos de codigo executados para adicionar os códigos QR à base de dados,
# além de gerar os arquivos de audio e três exemplos.

Info_De_Conexao = geradores.Info_De_Conexao
uppando_QR = False
gerando_mp3s = False
gerando_exemplos = False
uppando_noticias = False

if gerando_exemplos:
    geradores.gera_voz("Demonstração de voz", lingua="pt-BR", output="Exemplos\\exemploAudio.mp3")
    geradores.gera_voz("Sample voice test", lingua="en-US", output="Exemplos\\exampleAudio.mp3")
    geradores.gera_qr("exemplo", "Exemplos\\exemploQR.png")

if gerando_mp3s:
    arquivos = ["A_cartomante.txt", "A_pianista.txt"]
    for e in arquivos:
        geradores.gera_mp3zao(e, "Exemplos\\{}.mp3".format(e[:-4]))

# Indices de 1 a 4 representados nos QR_Codes indicam uma linha na tabela SQL
if uppando_QR:
    for i in range(1, 5):
        geradores.gera_qr(str(i), "qrTemp.png")
        qrBin = geradores.arquivo_para_binario("qrTemp.png")
        conexao = mysql.connector.connect(host=Info_De_Conexao["host"], user=Info_De_Conexao["user"],
                                          password=Info_De_Conexao["password"], database=Info_De_Conexao["database"])
        cursor = conexao.cursor()
        valores = (qrBin, i)
        cursor.execute("UPDATE livros SET QRCODE = %s WHERE ID = %s", valores)
        conexao.commit()
        cursor.close()
        conexao.close()

if uppando_noticias:
    geradores.gera_voz("Este é um exemplo de síntese de voz adicionada por um usuário.", output="userInput.mp3")


# Executa continuamente
while 1:
    try:
        # Estabelece uma conexao SQL
        conexao = mysql.connector.connect(host=Info_De_Conexao["host"], user=Info_De_Conexao["user"],
                                          password=Info_De_Conexao["password"], database=Info_De_Conexao["database"])
        cursor = conexao.cursor()
        # Lendo continuamente o campo de textos adicionados por usuarios
        cursor.execute("SELECT link, AUDIO FROM livros WHERE id = 10")
        resposta = cursor.fetchmany(2)
        mp3 = resposta[0][1]
        resposta = resposta[0][0]
        if resposta is not None and mp3 is None:
            # Ler texto, sintetizar, uppar para DB e limpar campo de input
            print("operando!")
            cursor.execute("UPDATE livros SET link = NULL WHERE id = 10")
            geradores.gera_voz(texto=resposta, output="tempUserAudio.mp3")
            mp3Binario = (geradores.arquivo_para_binario("tempUserAudio.mp3"),)
            cursor.execute("UPDATE livros SET AUDIO = %s WHERE id = 10", mp3Binario)
            conexao.commit()
        cursor.close()
        conexao.close()
    except mysql.connector.Error as e:
        print(e)
