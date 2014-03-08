#include<vector>
#include<iostream>
struct Z
{
	static const int arr = 1;

	static int eval(std::vector<int> a)
	{
		if (a.size() == arr)
			return 0;
		return -1;
	}

	template<class... T>
	static int eval(T... x)
	{
		std::vector<int> a{ x... };
		return eval(a);
	}
};

struct N
{
	static const int arr = 1;
	
	static int eval(std::vector<int> a)
	{
		if (a.size() == arr)
		{
			return a[0] + 1;
		}
		return -2;
	}

	template<class ...T>
	static int eval(T... x)
	{
		std::vector<int> a{ x... };
		return eval(a);
	}
};

template<int n, int k>
struct U
{
	static const int arr = n;

	static int eval(std::vector<int> a)
	{
		if (a.size() == arr)
		{
			return a[k - 1];
		}
		return -3;
	}

	template<class ...T>
	static int eval(T... x)
	{
		std::vector<int> a{ x... };
		return eval(a);
	}
};

template<class F, class g, class... G>
struct S
{
	static const int arr = g::arr;

	static int eval(std::vector<int> a)
	{
		if (a.size() == arr)
		{
			std::vector<int> tmp = { g::eval(a), G::eval(a)... };
			return F::eval(tmp);
		}
		return -4;
	}

	template<class... T>
	static int eval(T... x)
	{
		std::vector<int> a{ x... };
		return eval(a);
	}
};


template <class F, class G>
struct R
{
	static const int arr = G::arr - 1;

	static int eval(std::vector<int> a)
	{
		if (a.size() == arr)
		{
			int y = a[a.size() - 1];
			std::vector<int> tmp(a.begin(), --a.end());
			int t = F::eval(tmp);
			tmp.resize(arr + 1);
			for (int i = 0; i < y; ++i)
			{
				tmp[arr - 1] = i;
				tmp[arr] = t;
				t = G::eval(tmp);
			}
			
			return t;
		}
		return -5;
	}

	template<class... T>
	static int eval(T... x)
	{
		std::vector<int> a{ x... };
		return eval(a);
	}
};

template<class F>
struct M
{
	static const int arr = F::arr - 1;

	static int eval(std::vector<int> a)
	{
		if (a.size() == arr){
			int y = 0;
			std::vector<int> tmp = a;
			tmp.push_back(y);
			while (F::eval(tmp) != 0){
				++tmp[tmp.size() - 1];
			}
			return tmp[tmp.size() - 1];
		}
		return -6;
	}

	template<class... T>
	static int eval(T... x)
	{
		std::vector<int> a{ x... };
		return eval(a);
	}
};

template<class G, class... K>
void print_out(K... x)
{
	std::vector<int> a{ x... };
	std::cout << G::eval(a) << std::endl;
}

typedef S< R<Z,U<3,2> >, U<1,1>, U<1,1> >  DEC;
typedef R<U<1, 1>, S< N, U<3, 3>>> PLUS;
typedef R<U<1, 1>, S< DEC, U<3, 3>>> SUB;
typedef R<Z, S<PLUS, U<3,3>, U<3, 1>>> MULT;
typedef S< DEC, S< M< S<SUB, U<3,1>, S<MULT,  U<3, 2>, U<3, 3>>>>, S<N, U<2, 1> >, U<2, 2>>> DIV;
typedef S<SUB, U<2, 1>, S<MULT, U<2, 2>, DIV>> MOD;
typedef S <R<N, S<Z, U<3, 3>>>, U<1,1>, U<1,1>> NOT;
typedef S< S<NOT, NOT>,SUB> MORE;
typedef S<R< S<MORE, U<1,1>, S<N, Z>> ,S<S<S<S<NOT, NOT>, MULT>, S<MOD, U<3,1>, U<3,2>>, U<3,3>>, U<3,1>, S<S<N, N>, U<3, 2>>, U<3,3>>>, U<1,1>, S<DEC, S<DIV, U<1,1>, S<N, S<N, Z>>>>>  IS_PRIME;
typedef S<R<Z, S<PLUS, S<IS_PRIME, U<3,2>>, U<3,3>>>, U<1,1>, S<N, U<1,1>>> PRIMES_BEFORE;
typedef S<M<S<MORE, U<2,1>, S<PRIMES_BEFORE, U<2,2>>>>, N>  NTH_PRIME;
typedef R<S<N, Z> ,S<MULT, U<3, 3>, U<3, 1>>> POW;
typedef S<DEC, M<S< S<NOT, MOD>, U<3,2>, S<POW, U<3,1>, U<3,3>>>>> PLOG;
