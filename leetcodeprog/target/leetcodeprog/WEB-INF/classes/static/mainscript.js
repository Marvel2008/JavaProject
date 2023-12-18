document.addEventListener("DOMContentLoaded", function () {
    const tooltip = document.getElementById("tooltip");
    const userLink = document.querySelector(".callback");

    userLink.addEventListener("mouseover", function () {
        // Show the tooltip after a 0.1-second delay
        setTimeout(function () {
            // // Set random information for the tooltip
            // const randomInfo = getRandomInformation();
            // tooltip.textContent = randomInfo;

            // Position the tooltip next to the user link
            const linkRect = userLink.getBoundingClientRect();
            tooltip.style.top = linkRect.bottom + "px";
            tooltip.style.left = linkRect.left + "px";

            // Show the tooltip
            tooltip.style.display = "block";
        }, 160);
    });

    userLink.addEventListener("mouseout", function () {
        // Hide the tooltip on mouseout
        tooltip.style.display = "none";
    });

    // Function to get random information
    function getRandomInformation() {
        const infoArray = [
            "Information 1",
            "Information 2",
            "Information 3",
            // Add more random information as needed
        ];
        const randomIndex = Math.floor(Math.random() * infoArray.length);
        return infoArray[randomIndex];
    }
});