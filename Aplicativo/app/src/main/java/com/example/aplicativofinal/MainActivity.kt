package com.example.aplicativofinal

import android.content.ClipData
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativofinal.databinding.ActivityMainBinding
import android.view.MenuItem as ViewMenuItem

/** Parte do código é padrão, implementado pelo prórpio Android Studio
 *  com base no layout escolhido */

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /** A partir daqui inicia-se a parte do código implementada pelo grupo*/

    // Função acionada ao clicar-se no botão "configurações"
    fun onMenuItemClick(item: android.view.MenuItem) {
        startActivity(
            Intent(this, SettingsActivity::class.java).apply {
                // Inicia a tela(activity) de configurações, que não foi implementada nesse protótipo
            }
        )
    }

    // Acionada quando clica-se sobre o primeiro livro (superior esquerda)
    fun onHomeBook1Click(view: View) {
        // Inicia a tela(activity) em que toca-se áudio, enviando-se o código "1"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "1")
            }
        )
    }

    // Acionada quando clica-se sobre o segundo livro (superior direita)
    fun onHomeBook2Click(view: View) {
        // Inicia a tela(activity) em que toca-se áudio, enviando o código "2"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "2")
            }
        )
    }

    // Acionada quando clica-se sobre o terceiro livro (inferior esquerda)
    fun onHomeBook3Click(view: View) {
        // Inicia a tela(activity) em que toca-se áudio, enviando o código "3"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "3")
            }
        )
    }

    // Acionada quando clica-se sobre o quarto livro (inferior direita)
    fun onHomeBook4Click(view: View) {
        // Inicia a tela(activity) em que toca-se áudio, enviando o código "4"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "4")
            }
        )
    }

    // Acionada quando clica-se no título da primeira notícia
    fun onNotice1Click(view: View){
        // Inicia a tela(activity) em que toca-se o áudio, enviando o código "N1"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "N1")
            }
        )
    }

    // Acionada quando clica-se no título da segunda notícia
    fun onNotice2Click(view: View){
        // Inicia a tela(activity) em que toca-se o áudio, enviando o código "N2"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "N2")
            }
        )
    }

    // Acionada quando clica-se no título da terceira notícia
    fun onNotice3Click(view: View){
        // Inicia a tela(activity) em que toca-se o áudio, enviando o código "N3"
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                putExtra("code", "N3")
            }
        )
    }

    // Acionada quando clica-se no botão "SCAN QR CODE"
    fun onScanQRCodeButtonClick(view: View){
        startActivity(
            Intent(this, ScanQRCodeActivity::class.java).apply {
                // Inicia a tela(activity) de scan de QR CODE
            }
        )
    }

    // Acionada quando o botão "ENVIAR" é clicado
    fun onSendButtonClick(view: View){
        // Inicia a tela(activity) em que toca-se o áudio, enviando o conteúdo que foi digitado(message)
        startActivity(
            Intent(this, RunAudioActivity::class.java).apply {
                val message = findViewById<EditText>(R.id.digitBox).text.toString()
                putExtra("code", message)
            }
        )
    }

}