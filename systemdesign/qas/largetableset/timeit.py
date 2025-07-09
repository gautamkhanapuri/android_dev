import time
from functools import wraps

def time_it(func):
    """
    A decorator to measure the execution time of a function.
    """
    @wraps(func)
    def wrapper(*args, **kwargs):
        start_time = time.perf_counter() # Use perf_counter for precise measurements
        result = func(*args, **kwargs)
        end_time = time.perf_counter()
        execution_time = end_time - start_time
        print(f"Function '{func.__name__}' executed in {execution_time:.4f} seconds.")
        return result
    return wrapper

# Example usage:
@time_it
def my_long_running_function(n):
    """A function that simulates a long-running task."""
    total = 0
    for i in range(n):
        total += i * i
    return total

@time_it
def another_function(a, b):
    """A simple function."""
    time.sleep(0.1) # Simulate some work
    return a + b

if __name__ == "__main__":
    value = my_long_running_function(1_000_000)
    print(f"Result of long function: {value}")

    sum_val = another_function(5, 10)
    print(f"Result of another function: {sum_val}")
