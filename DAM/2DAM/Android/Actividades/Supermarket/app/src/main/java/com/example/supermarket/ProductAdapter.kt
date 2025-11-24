package com.example.supermarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val tvTotalPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
        private val btnRemove: Button = itemView.findViewById(R.id.btnRemove)

        fun bind(product: Product) {
            ivProductImage.setImageResource(product.image)
            tvProductName.text = product.name
            tvProductPrice.text = "Precio: ${product.price}€"
            tvQuantity.text = product.quantity.toString()
            tvTotalPrice.text = "Total: ${product.price * product.quantity}€"

            btnAdd.setOnClickListener {
                product.quantity++
                tvQuantity.text = product.quantity.toString()
                tvTotalPrice.text = "Total: ${product.price * product.quantity}€"
            }

            btnRemove.setOnClickListener {
                if (product.quantity > 0) {
                    product.quantity--
                    tvQuantity.text = product.quantity.toString()
                    tvTotalPrice.text = "Total: ${product.price * product.quantity}€"
                }
            }
        }
    }
}
