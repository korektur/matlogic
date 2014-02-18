#include<vector>
#include<iostream>
struct Z
{
	static const int arr = 1;

	static int eval(std::vector<int> a)
	{
		if (a.size() == arr)
			return 0;
		throw NULL;
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
		throw NULL;
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
		throw NULL;
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
		throw NULL;
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

		}
		throw NULL;
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
		throw NULL;
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

typedef R<U<1, 1>, S< N, U<3, 3>>> PLUS;

