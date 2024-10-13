package ir.hmb72.forexuser.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.local.welcome.PageItem
import ir.hmb72.forexuser.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    //Binding
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
            PageItem(R.string.title1, R.string.content1,R.drawable.img_contetn1,null),
            PageItem(R.string.title2, R.string.content2,null,R.drawable.bg_wave2),
            PageItem(R.string.title3, R.string.content3,null,R.drawable.bg_wave3),
            PageItem(R.string.title4, R.string.content4,null,R.drawable.bg_wave4)
        )
        binding.apply {
            //Viewpager

           val adapter = ViewPagerAdapter(items)
            viewPager.adapter = adapter
            adapter.setOnClickListener {
                findNavController().popBackStack(R.id.welcomeFragment,true)
                findNavController().navigate(R.id.action_to_loginFragment)
                //Save data in the dataStore to that this page will not be shown in future.
            }


            //Indicator
            indicator.setViewPager(viewPager)
            indicator.createIndicators(4,0)
            indicator.animatePageSelected(3)
            //Skip
            skip.setOnClickListener {
                findNavController().popBackStack(R.id.welcomeFragment,true)
                findNavController().navigate(R.id.action_to_loginFragment)
                //Save data in the dataStore to that this page will not be shown in future.
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
