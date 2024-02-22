package com.example.gamegenreshub.presentation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.gamegenreshub.R
import com.example.gamegenreshub.base.BaseFragment
import com.example.gamegenreshub.data.preferences.DeviceSharedPreferences
import com.example.gamegenreshub.databinding.FragmentMainBinding
import com.example.gamegenreshub.presentation.info.InfoAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment() {

    @Inject
    lateinit var sharedPreferences: DeviceSharedPreferences

    private lateinit var binding: FragmentMainBinding
    private lateinit var pagerAdapter: InfoAdapter

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showTheRightView()
        setupViewPager()
        setClickListeners()
    }

    private fun showTheRightView() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), options)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val isGuest = sharedPreferences.isSignedInAsGuest()

        if (account != null || isGuest) {
            navigateToNextScreen()
        }
    }

    private fun setClickListeners() {
        binding.apply {
            signInGoogleButton.setOnClickListener { signInWithGoogle() }
        }

        binding.signInGuestButton.setOnClickListener {
            sharedPreferences.setIsSignedInAsGuest(true)

            navigateToNextScreen()
        }
    }

    private fun signInWithGoogle() {

        try {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)

        } catch (e: Exception) {
            showErrorMessage(e.message)
        }
    }

    private fun setupViewPager() {
        binding.apply {
            pagerAdapter = InfoAdapter(childFragmentManager, lifecycle)
            binding.viewPager.adapter = pagerAdapter

            TabLayoutMediator(dotView, viewPager) { tab, position -> }.attach()
        }
    }

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            navigateToNextScreen()
        } else {
            showErrorMessage(getString(R.string.sign_in_error))
        }
    }

    private fun navigateToNextScreen() {
        val action = SignInFragmentDirections.actionSignInFragmentToGenresFragment(false)

        findNavController().navigate(action)
    }
}
