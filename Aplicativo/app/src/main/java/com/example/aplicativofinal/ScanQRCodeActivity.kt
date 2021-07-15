package com.example.aplicativofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

/** Para essa parte do código, utilizamos uma API disponibilizada no link:
 * https://github.com/yuriy-budiyev/code-scanner
 * Modificamos apenas os parâmetros de:
 * codeScanner.decodeCallback = DecodeCallback { (modificamos aqui) }*/

class ScanQRCodeActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    // String criada para receber o resultado do scan
    private lateinit var resultado : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qrcode)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            // A string "resultado" recebe o texto scaneado
            resultado = it.text
            when (resultado){
                // Quando "resultado" for o código de um dos livros
                "1", "2", "3", "4" -> {
                    // Inicia-se a activity que toca o áudio, enviando "resultado" como código
                    startActivity(
                        Intent(this, RunAudioActivity::class.java).apply {
                            putExtra("code", resultado)
                        }
                    )
                }
                // Caso contrário, ou seja, o scan não corresponde ao código de nenhum dos livros da nossa base de dados
                else -> {
                    // Mostra uma mensagem na tela na forma de Toast
                    runOnUiThread {
                        Toast.makeText(this, "O QR CODE não corresponde a nenhum de nossos livros", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}