package com.example.gamegenreshub.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gamegenreshub.R
import com.example.gamegenreshub.base.BaseFragment
import com.example.gamegenreshub.data.preferences.DeviceSharedPreferences
import com.example.gamegenreshub.databinding.FragmentSettingsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var sharedPreferences: DeviceSharedPreferences

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfo()

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), options)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.apply {
            signOutButton.setOnClickListener {
                signOut()
            }

            changeGenreButton.setOnClickListener {
                val action = SettingsFragmentDirections.actionSettingsFragmentToGenresFragment(true)
                findNavController().navigate(action)
            }

            returnToHomeButton.setOnClickListener {
                sharedPreferences.setIsSignedInAsGuest(false)
                showSignIn()
            }
        }
    }

    private fun setUserInfo() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            val displayName = account.displayName
            val email = account.email
            val photoUrl = account.photoUrl

            binding.apply {
                userGroup.visibility = View.VISIBLE
                userName.text = displayName
                binding.userEmail.text = email

                if (photoUrl != null) {
                    Glide.with(requireContext())
                        .load(photoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.default_user_image)
                        .error(R.drawable.default_user_image)
                        .into(userImage)
                } else {
                    userImage.setImageResource(R.drawable.default_user_image)
                }
            }
        } else {
            binding.apply {
                userGroup.visibility = View.GONE
            }
        }
    }

    private fun showSignIn() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToSignInFragment()
        findNavController().navigate(action)
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) { showSignIn() }
            .addOnFailureListener(requireActivity()) { e -> showErrorMessage(e.message) }
    }
}