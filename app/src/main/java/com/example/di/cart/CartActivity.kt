package com.example.di.cart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.di.databinding.ActivityCartBinding
import com.google.android.material.snackbar.Snackbar

class CartActivity : AppCompatActivity() {

    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: CartAdapter

    private var list: ArrayList<Product> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        list.add(Product(1, "Balaji Blissful Bath Soap", 1.0, 12.5))
        list.add(Product(2, "Balaji Freshly Brewed Coffee", 1.0, 15.0))
        list.add(Product(3, "Balaji Serene Spa Candle", 1.0, 20.0))
        list.add(Product(4, "Balaji Heavenly Body Lotion", 1.0, 25.0))
        list.add(Product(5, "Balaji Zen Garden Incense Sticks", 1.0, 30.0))
        list.add(Product(6, "Balaji Delightful Chai Tea", 1.0, 35.0))
        list.add(Product(7, "Balaji Radiant Facial Mask", 1.0, 40.0))
        list.add(Product(8, "Balaji Tranquil Sleep Aid", 1.0, 45.0))
        list.add(Product(9, "Balaji Energizing Sports Drink", 1.0, 50.0))
        list.add(Product(10, "Balaji Herbal Hair Oil", 1.0, 10.0))
        list.add(Product(11, "Balaji Sparkling Mineral Water", 1.0, 12.5))
        list.add(Product(12, "Balaji Harmony Essential Oil Blend", 1.0, 15.0))
        list.add(Product(13, "Balaji Gourmet Chocolate Truffles", 1.0, 20.0))
        list.add(Product(14, "Balaji Soothing Aloe Vera Gel", 1.0, 25.0))
        list.add(Product(15, "Balaji Nature's Trail Granola Bars", 1.0, 30.0))
        list.add(Product(16, "Balaji Royal Jasmine Perfume", 1.0, 35.0))
        list.add(Product(17, "Balaji Organic Green Tea", 1.0, 40.0))
        list.add(Product(18, "Balaji Nutty Almond Butter", 1.0, 45.0))
        list.add(Product(19, "Balaji Pure Honey", 1.0, 50.0))
        list.add(Product(20, "Balaji Exotic Spice Mix", 1.0, 10.0))

        calculateData()

        adapter = CartAdapter(this, list, object : CartAdapter.Callback {
            override fun sendQty(qty: Double, position: Int) {
                list[position].productQty = qty
            }

            override fun sendPrice(price: Double, position: Int) {
                list[position].productPrice = price
            }

            override fun sendTotal(total: Double, position: Int) {
                list[position].total = total
                calculateData()
            }
        })
        binding.rvCart.adapter = adapter

        // on below line we are creating a method to create item touch helper
        // method for adding swipe to delete functionality.
        // in this we are specifying drag direction and position to right
        // on below line we are creating a method to create item touch helper
        // method for adding swipe to delete functionality.
        // in this we are specifying drag direction and position to right
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deleteItem: Product = list[viewHolder.adapterPosition]

                // below line is to get the position
                // of the item at that positivon.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                list.removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                Snackbar.make(
                    binding.rvCart, "Deleted " + deleteItem.productNane, Snackbar.LENGTH_LONG
                ).setAction(
                    "Undo"
                ) {
                    // adding on click listener to our action of snack bar.
                    // below line is to add our item to array list with a position.
                    list.add(position, deleteItem)

                    // below line is to notify item is
                    // added to our adapter class.
                    adapter.notifyItemInserted(position)
                }.show()
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(binding.rvCart)

        binding.btnSubmit.setOnClickListener {
            list.forEachIndexed { index, product ->
                Log.e("DEV", "onCreate: ${product.total}")
            }
        }
    }

    private fun calculateData() {
        val total = list.sumOf {
            it.total ?: 0.0
        }
        binding.tvTotal.text = "Total : $total"
    }
}