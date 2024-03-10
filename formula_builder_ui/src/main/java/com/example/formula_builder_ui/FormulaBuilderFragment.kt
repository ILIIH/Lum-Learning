package com.example.formula_builder_ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.formula_builder_ui.adapters.CategoryIconAdapter
import com.example.formula_builder_ui.adapters.OperatorIconAdapter
import com.example.formula_builder_ui.databinding.FragmentFormulaBuilderBinding

class FormulaBuilderFragment : Fragment() {

    val chategory = ArrayList<operationIcon>(4)
    val greceAlpabet = ArrayList<operationIcon>(20)
    val mathOperators = ArrayList<operationIcon>(20)
    val mathSymbols = ArrayList<operationIcon>(20)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initChategory()
        initGreceAlpabet()
        initMathOperators()
        initMathSymbols()

        val view = FragmentFormulaBuilderBinding.inflate(inflater, container, false)

        val operatorsAdapter = OperatorIconAdapter { curBitmap: Bitmap ->
            view.formulaBuilderView.addOperand(
                Bitmap.createScaledBitmap(curBitmap, 120, 120, false)
            )
        }
        val categoryAdapter = CategoryIconAdapter { type: Category? ->
            view.operationIcons.layoutManager = GridLayoutManager(requireActivity(), 4)
            view.operationIcons.adapter = operatorsAdapter
            when (type) {
                Category.MATH_SYMBOLS -> {
                    operatorsAdapter.submitList(mathOperators)
                }
                Category.GRECE_ALPHABET -> {
                    operatorsAdapter.submitList(greceAlpabet)
                }
                Category.MATH_OPERATORS -> {
                    operatorsAdapter.submitList(mathSymbols)
                }
                else -> {
                    operatorsAdapter.submitList(mathOperators)
                }
            }
        }
        view.backButton.setOnClickListener {
            view.operationIcons.adapter = categoryAdapter
            categoryAdapter.submitList(chategory)
        }
        view.removeButton.setOnClickListener {
            view.formulaBuilderView.clear()
        }
        view.operationIcons.adapter = categoryAdapter
        categoryAdapter.submitList(chategory)

        return view.root
    }

    fun initMathSymbols() {
        mathSymbols.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.cos
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathSymbols.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.sin
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathSymbols.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.tan
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathSymbols.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.integral
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathSymbols.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.log
                    ),
                    120,
                    120,
                    false
                )
            )
        )
    }
    fun initGreceAlpabet() {
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.alpha
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.ex
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.y
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.beta
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.delta
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.delta_small
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.epslon
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.lambda
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.mu
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.omega
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.phi
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.phi_low
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.pi
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.psi
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.teta
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        greceAlpabet.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.zetta
                    ),
                    120,
                    120,
                    false
                )
            )
        )
    }

    fun initMathOperators() {
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.dividing_symbul
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.equal
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.minus_symbul
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.plus_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.square_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.zero_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.one_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.two_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.three_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.four_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.five_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )

        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.siz_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.eight_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
        mathOperators.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.nine_symbol
                    ),
                    120,
                    120,
                    false
                )
            )
        )
    }
    fun initChategory() {
        chategory.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.pi
                    ),
                    120,
                    120,
                    false
                ),
                categoryType = Category.GRECE_ALPHABET
            )
        )

        chategory.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.integral
                    ),
                    140,
                    140,
                    false
                ),
                categoryType = Category.MATH_OPERATORS
            )
        )

        chategory.add(
            operationIcon(
                image = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.dividing_symbul
                    ),
                    120,
                    120,
                    false
                ),
                categoryType = Category.MATH_SYMBOLS

            )
        )
    }
}

data class operationIcon(
    val image: Bitmap,
    val categoryType: Category? = null
)

enum class Category {
    GRECE_ALPHABET,
    MATH_OPERATORS,
    MATH_SYMBOLS
}
