package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.ReposotryAstro
import com.udacity.asteroidradar.Room.DataBaseAstriod
import com.udacity.asteroidradar.databinding.FragmentMainBinding



class MainFragment : Fragment() {

    //teel fragment
//    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
//    }

  //  Intialize Classes and pass factory
    private val dataBase by lazy {   DataBaseAstriod.getDataBase(requireContext()) }
    private val Repository by lazy {  ReposotryAstro(dataBase)}
    private val mainViewModel: MainViewModel by viewModels { AstroViewModelFactory(Repository) }

    private lateinit var  adapter: AstroidAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this


        //clicked
        adapter = AstroidAdapter(AstroidAdapter.OnClickListener{
             mainViewModel.pass(it)
        })

        binding.asteroidRecycler.adapter = adapter
        binding.viewModel = mainViewModel

        mainViewModel.navigateAstroidData.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                mainViewModel.clearPass()
            }
        })
        //Observer
        mainViewModel.UpdatedData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_all_menu ->{mainViewModel.week()}
            R.id.show_rent_menu ->{mainViewModel.today()}
            R.id.show_buy_menu ->{mainViewModel.saved()}
        }

        return true
    }

}
