package br.com.userregistration.application.dto

class Adress {
    private val code: String? = null
    private val street: String? = null
    private val number: String? = null
    private val district: String? = null

    fun getCode(): String? {return this.code};
    fun getStreet(): String? {return this.street};
    fun getNumber(): String? {return this.number};
    fun getDistrict(): String? {return this.district};

    override fun toString(): String {
        return "Adress(code=$code, street=$street, number=$number, district=$district)"
    }
}
