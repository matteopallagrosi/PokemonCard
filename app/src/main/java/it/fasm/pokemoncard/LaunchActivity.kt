package it.fasm.pokemoncard

import android.animation.*
import android.app.ActivityOptions
import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import it.fasm.pokemoncard.databinding.ActivityLaunchBinding


class LaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pvhX = PropertyValuesHolder.ofFloat("scaleX", 100f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleY", 100f)
        val scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(binding.ivSpring, pvhX, pvhY).apply {
            duration = 500
        }



        val rotateAnimation = ObjectAnimator.ofFloat(binding.ivSpring, "rotation", 360f).apply{
            repeatCount = 4
            duration = 150
        }

        val animator = AnimatorSet().apply {
            play(rotateAnimation).before(scaleAnimation)
        }

        animator.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                val i = Intent(this@LaunchActivity, MainActivity::class.java)
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this@LaunchActivity).toBundle())
            }
        })

        binding.ivSpring.setOnClickListener() {
            binding.tvTap.isVisible = false
            animator.start()
            binding.ivSpring.isClickable = false
        }
    }
}




