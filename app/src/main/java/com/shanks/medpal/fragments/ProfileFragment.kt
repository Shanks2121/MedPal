package com.shanks.medpal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shanks.medpal.activities.OnBoardingActivity
import com.shanks.medpal.database.NumbersDatabase
import com.shanks.medpal.databinding.FragmentProfileBinding
import com.shanks.medpal.models.Numbers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val db : NumbersDatabase
    get() = NumbersDatabase(requireContext())

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnAddNumber.setOnClickListener{

            val num = binding.etAddNumber.text.toString()

            GlobalScope.launch {
                try {
                    NumbersDatabase(requireContext()).numbersDao().insertNumber(Numbers(0,num))
                    binding.etAddNumber.text?.clear()
                } catch (e : Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }

            }


        }

        binding.btnDeleteAllNumber.setOnClickListener {
            GlobalScope.launch {
                try {
                    NumbersDatabase(requireContext()).numbersDao().deleteEveryThing()
                } catch (e : Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

            }
        }

        binding.btnSignOut.setOnClickListener {
            val intent = Intent(context,OnBoardingActivity::class.java )
            startActivity(intent)
        }



        return binding.root
    }


}