package com.voltaire.listadecompras.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.voltaire.listadecompras.R
import com.voltaire.listadecompras.databinding.FragmentOnboardingBinding
import com.voltaire.listadecompras.utils.IntroSlide
import com.voltaire.listadecompras.utils.IntroSlideAdapter
import com.voltaire.listadecompras.utils.PreferencesUtils
import com.voltaire.listadecompras.utils.extension.getDrawable
import com.voltaire.listadecompras.utils.extension.setVisible
import com.voltaire.listadecompras.utils.extension.viewBinding
import com.voltaire.listadecompras.utils.functions.toastCreator

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    // VEJA VIDEO BINDING AUTOMATICO: https://youtu.be/qivrch6qxQw
    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideViewPager = binding.fragmentOnboardingSlideViewpager


        val items = listOf(
            IntroSlide(
                icon = R.raw.organize,
                title = R.string.organize_title,
                description = R.string.organize_description
            ),
            IntroSlide(
                icon = R.raw.controle,
                title = R.string.controle_title,
                description = R.string.controle_description
            ),
            IntroSlide(
                icon = R.raw.surpresa,
                title = R.string.surprise_title,
                description = R.string.surprise_description
            ),
            IntroSlide(
                icon = R.raw.swipe,
                title = R.string.swipe_title,
                description = R.string.swipe_description
            )
        )

        val introSlideAdapter = IntroSlideAdapter(items)
        slideViewPager.adapter = introSlideAdapter
        val indicators = view.findViewById<LinearLayout>(R.id.fragment_onboading_slide_indicator)
        // CRIAR QUANTIDADE CORRETA DE INDICADORES
        setupIndicators(indicators, introSlideAdapter.itemCount)
        // COLOCAR SELEçÃO NO PRIMEIRO ITEM
        setCurrentIndicator(indicators, 0)
        // PARA SABER A HORA DE EXIBIR O BOTÃO FINAL NO ONBOARDING SCREEN
        slideViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(indicators, position)
                if (introSlideAdapter.itemCount - 1 == position) {
                    binding.fragmentOnboardingGo.setVisible(true)
                }
            }
        })

        // QUANDO TOCAR NO BOTÃO, EXECUTE A AçÃO DESEJADA
        binding.fragmentOnboardingGo.setOnClickListener {
            PreferencesUtils(requireContext()).saveResponse()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupIndicators(indicatorContainer: LinearLayout, indicatorCount: Int) {
        // CRIAR LISTA DE INDICADORES
        val indicators = arrayOfNulls<ImageView>(indicatorCount)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // DIMENSIONAR OS INDICADORES
        val indicatorSpace = resources.getDimensionPixelSize(R.dimen.slide_indicator_space)
        params.setMargins(indicatorSpace, 0, 0, 0)
        // ATRIBUIR BACKGROUND AOS INDICADORES
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i]?.apply {
                this.setImageDrawable(getDrawable(R.drawable.indicator_unselected))
                this.layoutParams = params
            }
            indicatorContainer.addView(indicators[i])
        }
    }

    // SELECIONAR O INDICADOR DE ACORDO COM A PAGINA ATUAL

    private fun setCurrentIndicator(indicatorContainer: LinearLayout, index: Int) {
        for (i in 0 until indicatorContainer.childCount) {
            val img = indicatorContainer[i] as? ImageView
            when (index == i) {
                true -> img?.setImageDrawable(getDrawable(R.drawable.indicator_selected))
                else -> img?.setImageDrawable(getDrawable(R.drawable.indicator_unselected))
            }
        }
    }
}