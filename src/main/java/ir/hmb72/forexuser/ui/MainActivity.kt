package ir.hmb72.forexuser.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.databinding.ActivityMainBinding
import ir.hmb72.forexuser.utils.setupVisible

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    //Other
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Init nav host
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        //Bottom nav main
        binding.bottomNavMain.apply {
            setupWithNavController(navHost.navController)
            //Disable double click on items
            setOnNavigationItemReselectedListener { }
        }
        //Bottom nav my list
   /*     binding.bottomNavMyList.apply {
            setupWithNavController(navHost.navController)
            //Disable double click on items
            setOnNavigationItemReselectedListener { }
        }*/
        //Gone bottom nav
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.apply {
                bottomNavMain.isVisible = false
          /*      bottomNavMyList.isVisible = false*/
                when (destination.id) {
                    //show bottom one for
                    R.id.homeFragment,
                    R.id.shopFragment,
                    R.id.signalsFragment,
                    R.id.profileFragment,

                    -> bottomNavMain.isVisible = true

//                    //show bottom two
//                    R.id.myListFragment,
//                    R.id.journalFragment,
//                    R.id.psychologyFragment,
//                    R.id.resumeFragment,
//                    R.id.riskManagementFragment
//                    -> bottomNavMyList.isVisible = true
//                    else ->  {
//                        bottomNavMain.isVisible = false
//                        bottomNavMyList.isVisible = false
//                    }

                }

            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    fun hideBottomNavigation() {
//        binding.bottomNavMyList.setupVisible(false)
//    }
//
//    fun showBottomNavigation() {
//        binding.bottomNavMyList.setupVisible(true)
//    }
}