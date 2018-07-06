package ir.paad.audiobook.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock
import ir.paad.audiobook.models.events.Progress
import org.greenrobot.eventbus.EventBus

class DownloadService : Service(), FetchListener {


    private val tag = "DownloadService"
    private lateinit var fetchDownloader: Fetch


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        EventBus.getDefault().register(this)

        val config = FetchConfiguration.Builder(this)
                .enableAutoStart(false)
                .setDownloadConcurrentLimit(1)
                .build()
        fetchDownloader = Fetch.Impl.getInstance(config)
        fetchDownloader.addListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleNewDownloadRequest()
        return Service.START_NOT_STICKY
    }

    private fun handleNewDownloadRequest() {
        val request = Request("url", "file path (remember to create file before download)")
        request.networkType = NetworkType.ALL
        request.priority = Priority.HIGH

        fetchDownloader.enqueue(request, { request ->
            Log.e(tag, "enqueued successfully")
        }, { error ->
            Log.e(tag, "failed to enqueue request : ${error}")
        })

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        fetchDownloader.removeListener(this)
        super.onDestroy()
    }


    override fun onAdded(download: Download) {
    }

    override fun onCancelled(download: Download) {
    }

    override fun onCompleted(download: Download) {

    }

    override fun onDeleted(download: Download) {
    }

    override fun onDownloadBlockUpdated(download: Download, downloadBlock: DownloadBlock, totalBlocks: Int) {
    }

    override fun onError(download: Download) {
    }

    override fun onPaused(download: Download) {
    }

    override fun onProgress(download: Download, etaInMilliSeconds: Long, downloadedBytesPerSecond: Long) {
        EventBus.getDefault().post(Progress(id = download.id, progress = download.progress))
    }

    override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
    }

    override fun onRemoved(download: Download) {
    }

    override fun onResumed(download: Download) {
    }

}