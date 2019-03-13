package wsdt.ml.predict

import org.nd4j.linalg.api.ndarray.INDArray
import wsdt.ml.train.Trainer.DATA_PATH
import java.io.File
import java.io.IOException


object Predictor {

    // private const val THRESHOLD_ACCURACY = 0.50

    @Throws(Exception::class)
    fun predict(filename: String, basePath: String = "resources"): List<BirthmarkType> {
        DATA_PATH = basePath // Assign other base path for server architecture or keep default

        val vg16ForCat = VG16()
        vg16ForCat.loadModel()

        try {
            val selectedFile = File("$DATA_PATH/uploaded/$filename.jpg")
            val output: INDArray = vg16ForCat.detectType(selectedFile)

            print("CONFIDENCE:\nBenign: " + output.getDouble(0) +
                    "\nMalignant: " + output.getDouble(1) + "\n")

            return listOf(
                    Benign(output.getDouble(0)),
                    Malignant(output.getDouble(1)),
                    NotKnown(0.0)
            )

            /*
            return if (output.getDouble(0) > THRESHOLD_ACCURACY) {
                BirthmarkType.BASAL_CELL_CARCINOMA
            } else if (output.getDouble(1) > THRESHOLD_ACCURACY) {
                BirthmarkType.MELANOMA
            } else if (output.getDouble(2) > THRESHOLD_ACCURACY){
                BirthmarkType.SQUAMOUS_CELL_CARCINOMA
            } else {
                BirthmarkType.NOT_KNOWN
            }*/

        } catch (e1: IOException) {
            throw RuntimeException(e1)
        }

    }
}
