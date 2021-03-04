package com

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.ynov_lyon_bde.R
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class Scanner : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val scanner = findViewById<DecoratedBarcodeView>(R.id.scanner)
        scanner.initializeFromIntent(intent)
    }

//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK){
//            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//            if (result != null) {
//                if (result.contents == null) {
//                    Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(activity, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
//                }
//            } else {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//        }
//    }
}
