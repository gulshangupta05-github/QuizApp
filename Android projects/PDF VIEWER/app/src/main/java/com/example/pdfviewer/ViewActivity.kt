package com.example.pdfviewer

import android.content.ContentValues.TAG
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.android.synthetic.main.activity_view.*
import java.io.File

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        if (intent != null) {
            val viewtype = intent.getStringExtra("ViewType")
            if (!TextUtils.isEmpty(viewtype) || viewtype != null) {
                if (viewtype.equals("assets")) {
                    pdf_viewer.fromAsset("gulshan_aktu even_result.pdf")
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)// double tap zoom
                        .onDraw { canvas, pageWidth, pageHeight, displayPage -> }
                        .onDrawAll { canvas, pageWidth, pageHeight, displayPage -> }

                        .onPageChange { page, pageCount ->

                            // Enter your code here
                        }
                        .onPageError { page, t ->
                            Toast.makeText(
                                this,
                                "Error while opening page $page",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d(TAG, "onCreate: " + t.localizedMessage)
                        }
                        .onTap { false }
                        .onRender { nbPages, pageWidth, pageHeight ->
                            pdf_viewer.fitToWidth()
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.RED)
                        .load()
                } else if (viewtype.equals("storage")) {
                    val selectPdf = Uri.parse(intent.getStringExtra("FileUri"))

                    pdf_viewer.fromUri(selectPdf)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)// double tap zoom
                        .onDraw { canvas, pageWidth, pageHeight, displayPage -> }
                        .onDrawAll { canvas, pageWidth, pageHeight, displayPage -> }

                        .onPageChange { page, pageCount ->

                            // Enter your code here
                        }
                        .onPageError { page, t ->
                            Toast.makeText(
                                this,
                                "Error while opening page $page",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d(TAG, "onCreate: " + t.localizedMessage)
                        }
                        .onTap { false }
                        .onRender { nbPages, pageWidth, pageHeight ->
                            pdf_viewer.fitToWidth()
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.RED)
                        .load()
                } else if (viewtype.equals("internet")) {
                    progressBar.visibility = View.VISIBLE

                    FileLoader.with(this)
                        .load("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf",false )
                        .fromDirectory("PDFFile", FileLoader.DIR_INTERNAL)
                        .asFile(object : FileRequestListener<File> {
                            override fun onLoad(
                                request: FileLoadRequest?,
                                response: FileResponse<File>?
                            ) {
                                            progressBar.visibility= View.GONE
                                val pdfFile = response!!.body

                                //yha se lekar .load() tak tino button par same function work karega
                                pdf_viewer.fromFile(pdfFile)
                                    .password(null)
                                    .defaultPage(0)
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)// double tap zoom
                                    .onDraw { canvas, pageWidth, pageHeight, displayPage -> }
                                    .onDrawAll { canvas, pageWidth, pageHeight, displayPage -> }

                                    .onPageChange { page, pageCount ->

                                        // Enter your code here
                                    }
                                    .onPageError { page, t ->
                                        Toast.makeText(this@ViewActivity,"Error while opening page $page", Toast.LENGTH_SHORT).show()
                                        Log.d(TAG, "onCreate: " + t.localizedMessage)
                                    }
                                    .onTap { false }
                                    .onRender { nbPages, pageWidth, pageHeight ->
                                        pdf_viewer.fitToWidth()
                                    }
                                    .enableAnnotationRendering(true)
                                    .invalidPageColor(Color.RED)
                                    .load()
                            }

                            override fun onError(request: FileLoadRequest?, t: Throwable?) {
                                Toast.makeText(
                                    this@ViewActivity,
                                    "" + t!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                progressBar.visibility = View.GONE
                            }

                        })
                }


            }
        }

    }
}