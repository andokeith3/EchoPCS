package com.example.aplicativofinal

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

/** Essa activity corresponde à tela em que o áudio é tocado
 * e foi totalmente programada pelo grupo */

class RunAudioActivity : AppCompatActivity() {

    // Declaração de variáveis globais
    lateinit var slider : SeekBar
    private lateinit var duration : TextView
    lateinit var position : TextView
    private lateinit var display: ImageView
    private var mediaPlayer: MediaPlayer? = null

    // Função padrão do andriod studio, é iniciada na criação da tela
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_audio)

        // Variáveis declaradas acima recebem o valor de views, sendo elas:
        // O controle deslizante
        slider = findViewById(R.id.seekBar)
        // O tempo total, mostrado à direita do slider
        duration = findViewById(R.id.duration)
        // O tempo atual, mostrado à esquerda do slider que, no entando,
        // não foi implementado seu funciomento exceto quando o slider é usado
        // ou quando os botões avançar ou voltar são clicados
        position = findViewById(R.id.currentPos)
        // A imagem que aparece acima do slider
        display = findViewById(R.id.display)

        // Aqui é testada a string enviada por quem inicializou esta tela, ou seja,
        // o "código" citado nas outras activitys
        when (intent.getStringExtra("code")) {
            // Aqui ocorreria comunicação com o servidor, mas implementamos
            // apenas alguns casos para simular o funcionamento

            // Se for igual a 'x', a imagem recebe a capa do livro e o arquivo
                // de áudio correspondente
            "1" -> {
                display.setImageResource(R.drawable.a_carteira)
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.a_carteira_audio)
            }
            "2" -> {
                display.setImageResource(R.drawable.a_cartomante)
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.a_cartomante_audio)
            }
            "3" -> {
                display.setImageResource(R.drawable.a_pianista)
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.a_pianista_audio)
            }
            "4" -> {
                display.setImageResource(R.drawable.o_cortico)
                // Neste caso não produzimos um áudio correspondente
            }
            // Se for igual a 'Nx', ou seja, uma noticia, recebe a imagem do jornal e
            // o arquivo de áudio correspondente
            "N1" -> {
                display.setImageResource(R.drawable.noticia)
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.noticia1_audio)
            }
            "N2", "N3" -> {
                display.setImageResource(R.drawable.noticia)
                // Neste caso não produzimos um áudio correspondente
            }
            // Se nao for nenhum desses, significa que é um input do usuário,
            // então colocamos a imagem do fone e o texto seria enviado para
            // o servidor para ser transformado
            else -> {
                display.setImageResource(R.drawable.fone)
                // Como não implementamos a comunicação com o servidor, colocamos apenas esse
                // input como exemplo para a apresentação
                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.user_input_audio)
            }
        }

        // O valor maximo do slider recebe a duração do áudio
        slider.max = mediaPlayer?.duration!!
        // Aqui a duração é transformada em minutos, segundos e, se necessário, horas,
        // e mostrada na text view correspondente à duração(duration)
        val aux = mediaPlayer!!.duration/1000
        val sec = aux%60
        var min = aux/60
        var hor : Int? = null

        if (min >= 60){
            hor = min/60
            min %= 60
        }

        var secS : String = if (sec < 10) {
            "0$sec"
        } else {
            sec.toString()
        }

        var minS : String = if (min < 10) {
            "0$min"
        } else {
            min.toString()
        }

        if (hor == null) {
            duration.text = "$minS:$secS"
        } else {
            duration.text = "$hor:$minS:$secS"
        }

        // Aqui corresponde a quando o usuário interage com o controle deslizante
        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            // Esta função é acionada enquanto o usuário arrasta o slider
            // Neste caso, toda a programação implementada corresponde à mostrar, no text view
            // correspondente ao tempo atual(position), o tempo para o qual será pulado quando
            // o usuário soltar a barra
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val aux = progress/1000
                val sec = aux%60
                var min = aux/60
                var hor : Int? = null

                if (min >= 60){
                    hor = min/60
                    min %= 60
                }

                var secS : String = if (sec < 10) {
                    "0$sec"
                } else {
                    sec.toString()
                }

                var minS : String = if (min < 10) {
                    "0$min"
                } else {
                    min.toString()
                }

                if (hor == null) {
                    position.text = "$minS:$secS"
                } else {
                    position.text = "$hor:$minS:$secS"
                }
            }

            // Esta função corresponde a quando toca na barra, porém não foi usada
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            // Está função corresponde à quando o usuário solta a barra
            // Neste caso, o tempo de execução do áudio pula para o tempo
            // correspondente ao progresso do slider
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer?.seekTo(slider.progress)
            }
        })

    }

    // Iniciada quando o botão de play é clicado
    fun onPlayButtonClick(view: View){
        // Aqui aplica-se a função start() à media player
        mediaPlayer?.start()
    }

    // Iniciada quando o botão de pause é clicado
    fun onPauseButtonClick(view: View){
        // Aplica-se à media player o estado de pause()
        mediaPlayer?.pause()
    }

    // Iniciada quando o botão de voltar é clicado
    fun onGoBackButtonClick(view: View){
        // O tempo do áudio volta 10 segundos
        mediaPlayer?.seekTo(mediaPlayer!!.currentPosition - 10000)
        // Mesmo funciamento de enquanto se arrasta o slider
        val aux = mediaPlayer!!.currentPosition/1000
        val sec = aux%60
        var min = aux/60
        var hor : Int? = null

        if (min >= 60){
            hor = min/60
            min %= 60
        }

        var secS : String = if (sec < 10) {
            "0$sec"
        } else {
            sec.toString()
        }

        var minS : String = if (min < 10) {
            "0$min"
        } else {
            min.toString()
        }

        if (hor == null) {
            position.text = "$minS:$secS"
        } else {
            position.text = "$hor:$minS:$secS"
        }
        // O progresso do slider tambem regride o correspondente aos 10 segundos
        slider.progress = mediaPlayer!!.currentPosition
    }

    // Iniciada quando o botão de avançar é clicado
    fun onAdvanceButtonClick(view: View){
        // O tempo do áudio avança 10 segundos
        mediaPlayer?.seekTo(mediaPlayer!!.currentPosition + 10000)
        // Mesmo funciamento de enquanto se arrasta o slider
        val aux = mediaPlayer!!.currentPosition/1000
        val sec = aux%60
        var min = aux/60
        var hor : Int? = null

        if (min >= 60){
            hor = min/60
            min %= 60
        }

        var secS : String = if (sec < 10) {
            "0$sec"
        } else {
            sec.toString()
        }

        var minS : String = if (min < 10) {
            "0$min"
        } else {
            min.toString()
        }

        if (hor == null) {
            position.text = "$minS:$secS"
        } else {
            position.text = "$hor:$minS:$secS"
        }
        // O progresso do slider tambem avança o correspondente aos 10 segundos
        slider.progress = mediaPlayer!!.currentPosition
    }

    // Iniciada quando a activity é finalizada, ou seja, ao sair dessa tela
    override fun onStop() {
        super.onStop()
        // Nesse caso, o media player recebe o estado stop(), é liberado, e volta estar vazio
        // Necessário para que não haja bugs, um media player sempre deve ser liberado ao
        // final de seu uso
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}



