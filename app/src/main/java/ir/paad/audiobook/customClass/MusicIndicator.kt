package ir.paad.audiobook.customClass

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ir.paad.audiobook.R
import ir.paad.audiobook.utils.Colors
import java.util.*

class MusicIndicator : View {

    private var paint: Paint? = null
    private var stepNum = 10

    private var duration = 3000
    private var barNum = 3
    private var barColor = -0x1000000

    private var viewHeight: Int = 0
    private var autoStart = false
    private var animList: MutableList<ValueAnimator>? = null


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MusicIndicator, 0, 0)
        try {
            barNum = ta.getInt(R.styleable.MusicIndicator_bar_num, 3)
            stepNum = ta.getInt(R.styleable.MusicIndicator_step_num, 10)
            duration = ta.getInt(R.styleable.MusicIndicator_duration, 3000)
            autoStart = ta.getBoolean(R.styleable.MusicIndicator_auto_start, false)
            barColor = ta.getColor(R.styleable.MusicIndicator_bar_color, Colors(context).colorPrimaryDark)
        } finally {
            ta.recycle()
        }

        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun init() {
        paint = Paint()
        paint!!.color = barColor
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        startAnim()
    }


    private fun startAnim() {
        if (autoStart) {
            val floatList = getGraduateFloatList(stepNum, viewHeight)
            generateAnim(floatList, barNum)
            invalidate()
        }
    }

    private fun stopAnim() {
        animList?.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (autoStart) {
            drawIndicator(canvas, barNum)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        this.setMeasuredDimension(viewWidth, viewHeight)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    private fun getGraduateFloatList(arraySize: Int, max: Int): MutableList<Float> {
        val floatList = ArrayList<Float>()
        val dividedMax = (max / arraySize).toDouble()

        for (i in 1..arraySize) {
            val a = i * dividedMax
            floatList.add(a.toFloat())
        }
        floatList[floatList.size - 1] = floatList[0]

        return floatList
    }


    private fun generateAnim(floatList: MutableList<Float>, barNum: Int) {

        animList = ArrayList()

        for (i in 0 until barNum) {
            floatList.shuffle()
            floatList[floatList.size - 1] = floatList[0]
            val floatArray = FloatArray(floatList.size)
            var j = 0
            for (f in floatList) {
                floatArray[j++] = f
            }

            val anim = ValueAnimator.ofFloat(*floatArray)
            anim.duration = duration.toLong()
            anim.repeatCount = ValueAnimator.INFINITE
            anim.addUpdateListener { invalidate() }
            anim.start()

            animList!!.add(anim)
        }
    }


    private fun drawIndicator(canvas: Canvas, barNum: Int) {

        val spaceWidth = canvas.width * 0.3 / (barNum - 1)
        val barWidth = canvas.width * 0.3 / barNum
        val sumWidth = spaceWidth + barWidth

        for (i in 0 until barNum - 1) {
            val height = animList!![i].animatedValue as Float
            canvas.drawRect(
                    (i * sumWidth).toFloat(), // left
                    canvas.height - height, //height, // top
                    (i * sumWidth + barWidth).toFloat(), // right
                    canvas.height.toFloat(), // bottom
                    paint!! // Paint
            )

            if (i == barNum - 2) {
                val heightLast = animList!![i + 1].animatedValue as Float
                canvas.drawRect(
                        ((i + 1) * sumWidth).toFloat(), // left
                        canvas.height - heightLast, //height, // top
                        ((i + 1) * sumWidth + barWidth).toFloat(), // right
                        canvas.height.toFloat(), // bottom
                        paint!! // Paint
                )
            }
        }
    }

    fun setStepNum(stepNum: Int) {
        this.stepNum = stepNum
    }

    fun setDuration(duration: Int) {
        this.duration = duration
    }

    fun setBarNum(barNum: Int) {
        this.barNum = barNum
    }

    fun setBarColor(barColor: Int) {
        this.barColor = barColor
    }

    fun setAutoStart(b: Boolean) {
        this.autoStart = b
        if (b) {
            startAnim()
        } else {
            stopAnim()
        }
    }
    fun getAutoStart():Boolean{
        return autoStart
    }
}