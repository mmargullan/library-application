document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("register-form");
    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const user = {
            username: document.getElementById("username").value,
            password: document.getElementById("password").value
        };

        try {
            const response = await fetch("/api/users/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user)
            });

            if (response.ok) {
                alert("OK");
            } else {
                alert("Failure");
            }
        } catch (error) {
            console.error("Registration failed", error);
            alert("Error occurred");
        }
    });
});