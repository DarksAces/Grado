////////////////////////////////////////////////////////////
// Headers
////////////////////////////////////////////////////////////
#include <SFML/Graphics.hpp>
#include <SFML/Audio.hpp>
#include <cmath>
#include <ctime>
#include <cstdlib>
#include <string>


////////////////////////////////////////////////////////////
/// Entry point of application
///
/// \return Application exit code
///
////////////////////////////////////////////////////////////
int main()
{
	std::srand(static_cast<unsigned int>(std::time(NULL)));

	// Define some constants
	const float pi = 3.14159f;
	const int gameWidth = 800;
	const int gameHeight = 600;
	sf::Vector2f paddleSize(25, 100);
	float ballRadius = 10.f;

	// Create the window of the application
	sf::RenderWindow window(sf::VideoMode(gameWidth, gameHeight, 32), "SFML Pong",
		sf::Style::Titlebar | sf::Style::Close);
	window.setVerticalSyncEnabled(true);

	// Load the sounds used in the game
	sf::SoundBuffer ballSoundBuffer;
	if (!ballSoundBuffer.loadFromFile("resources/ball.wav"))
		return EXIT_FAILURE;
	sf::Sound ballSound(ballSoundBuffer);

	// Create the left paddle
	sf::RectangleShape leftPaddle;
	leftPaddle.setSize(paddleSize - sf::Vector2f(3, 3));
	leftPaddle.setOutlineThickness(3);
	leftPaddle.setOutlineColor(sf::Color::Black);
	leftPaddle.setFillColor(sf::Color(100, 100, 200));
	leftPaddle.setOrigin(paddleSize / 2.f);

	// Create the right paddle
	sf::RectangleShape rightPaddle;
	rightPaddle.setSize(paddleSize - sf::Vector2f(3, 3));
	rightPaddle.setOutlineThickness(3);
	rightPaddle.setOutlineColor(sf::Color::Black);
	rightPaddle.setFillColor(sf::Color(200, 100, 100));
	rightPaddle.setOrigin(paddleSize / 2.f);

	// Create the ball
	sf::CircleShape ball;
	ball.setRadius(ballRadius - 3);
	ball.setOutlineThickness(3);
	ball.setOutlineColor(sf::Color::Black);
	ball.setFillColor(sf::Color::White);
	ball.setOrigin(ballRadius / 2, ballRadius / 2);

	// Create the multiplier bonus
	sf::CircleShape multiplierBonus;
	multiplierBonus.setRadius(15);
	multiplierBonus.setFillColor(sf::Color(255, 215, 0)); // Gold color
	multiplierBonus.setOrigin(15, 15);
	bool multiplierActive = false;
	bool multiplierVisible = false;
	int currentMultiplier = 1;
	float multiplierDuration = 10.0f; // Duration in seconds
	sf::Clock multiplierDurationClock;

	// Load the text font
	sf::Font font;
	if (!font.loadFromFile("resources/sansation.ttf"))
		return EXIT_FAILURE;

	// Initialize the pause message
	sf::Text pauseMessage;
	pauseMessage.setFont(font);
	pauseMessage.setCharacterSize(40);
	pauseMessage.setPosition(170.f, 150.f);
	pauseMessage.setFillColor(sf::Color::White);
	pauseMessage.setString("Welcome to SFML pong!\nPress space to start the game");

	// Initialize the score display
	int playerScore = 0;
	sf::Text scoreText;
	scoreText.setFont(font);
	scoreText.setCharacterSize(30);
	scoreText.setPosition(30.f, 20.f);
	scoreText.setFillColor(sf::Color::White);
	scoreText.setString("Score: 0");

	// Initialize the multiplier display
	sf::Text multiplierText;
	multiplierText.setFont(font);
	multiplierText.setCharacterSize(25);
	multiplierText.setPosition(30.f, 60.f);
	multiplierText.setFillColor(sf::Color(255, 215, 0)); // Gold color
	multiplierText.setString("");

	// Initialize the time display
	sf::Text timeText;
	timeText.setFont(font);
	timeText.setCharacterSize(25);
	timeText.setPosition(gameWidth - 150.f, 20.f);
	timeText.setFillColor(sf::Color::White);
	timeText.setString("Time: 0:00");

	// Define the paddles properties
	sf::Clock AITimer;
	const sf::Time AITime = sf::seconds(0.1f);
	const float paddleSpeed = 400.f;
	float rightPaddleSpeed = 0.f;
	const float ballSpeed = 400.f;
	float ballAngle = 0.f; // to be changed later

	// Game time tracking
	sf::Clock gameClock;
	int gameSeconds = 0;
	int gameMinutes = 0;

	// Multiplier spawn timing
	sf::Clock multiplierSpawnClock;
	float nextMultiplierTime = 30.0f + static_cast<float>(std::rand() % 60); // Random between 30s and 90s

	sf::Clock clock;
	bool isPlaying = false;
	while (window.isOpen())
	{
		// Handle events
		sf::Event event;
		while (window.pollEvent(event))
		{
			// Window closed or escape key pressed: exit
			if ((event.type == sf::Event::Closed) ||
				((event.type == sf::Event::KeyPressed) && (event.key.code == sf::Keyboard::Escape)))
			{
				window.close();
				break;
			}

			// Space key pressed: play
			if ((event.type == sf::Event::KeyPressed) && (event.key.code == sf::Keyboard::Space))
			{
				if (!isPlaying)
				{
					// (re)start the game
					isPlaying = true;
					clock.restart();
					gameClock.restart();
					multiplierSpawnClock.restart();
					gameSeconds = 0;
					gameMinutes = 0;

					// Reset the position of the paddles and ball
					leftPaddle.setPosition(10 + paddleSize.x / 2, gameHeight / 2);
					rightPaddle.setPosition(gameWidth - 10 - paddleSize.x / 2, gameHeight / 2);
					ball.setPosition(gameWidth / 2, gameHeight / 2);

					// Reset multiplier status
					multiplierVisible = false;
					multiplierActive = false;
					currentMultiplier = 1;
					multiplierText.setString("");
					nextMultiplierTime = 30.0f + static_cast<float>(std::rand() % 60);

					// Reset the score if starting a new game after losing or winning
					if (pauseMessage.getString() != "Welcome to SFML pong!\nPress space to start the game")
					{
						playerScore = 0;
						scoreText.setString("Score: 0");
					}

					// Reset the ball angle
					do
					{
						// Make sure the ball initial angle is not too much vertical
						ballAngle = (std::rand() % 360) * 2 * pi / 360;
					} while (std::abs(std::cos(ballAngle)) < 0.7f);
				}
			}
		}

		if (isPlaying)
		{
			float deltaTime = clock.restart().asSeconds();

			// Update game time
			float gameTime = gameClock.getElapsedTime().asSeconds();
			gameMinutes = static_cast<int>(gameTime) / 60;
			gameSeconds = static_cast<int>(gameTime) % 60;

			// Format time as M:SS
			std::string timeString = "Time: " + std::to_string(gameMinutes) + ":";
			if (gameSeconds < 10) timeString += "0";
			timeString += std::to_string(gameSeconds);
			timeText.setString(timeString);

			// Check if it's time to spawn a multiplier
			if (!multiplierVisible && !multiplierActive && multiplierSpawnClock.getElapsedTime().asSeconds() >= nextMultiplierTime)
			{
				// Spawn a new multiplier at random position (away from edges)
				int x = 100 + std::rand() % (gameWidth - 200);
				int y = 100 + std::rand() % (gameHeight - 200);
				multiplierBonus.setPosition(static_cast<float>(x), static_cast<float>(y));

				// Choose a random multiplier value (x2, x3, or x5)
				int multiplierValues[3] = { 2, 3, 5 };
				currentMultiplier = multiplierValues[std::rand() % 3];

				multiplierVisible = true;

				// Set the next spawn time
				nextMultiplierTime = 30.0f + static_cast<float>(std::rand() % 60);
				multiplierSpawnClock.restart();
			}

			// Check if active multiplier has expired
			if (multiplierActive && multiplierDurationClock.getElapsedTime().asSeconds() >= multiplierDuration)
			{
				multiplierActive = false;
				currentMultiplier = 1;
				multiplierText.setString("");
			}

			// Move the player's paddle
			if (sf::Keyboard::isKeyPressed(sf::Keyboard::Up) &&
				(leftPaddle.getPosition().y - paddleSize.y / 2 > 5.f))
			{
				leftPaddle.move(0.f, -paddleSpeed * deltaTime);
			}
			if (sf::Keyboard::isKeyPressed(sf::Keyboard::Down) &&
				(leftPaddle.getPosition().y + paddleSize.y / 2 < gameHeight - 5.f))
			{
				leftPaddle.move(0.f, paddleSpeed * deltaTime);
			}

			// Move the computer's paddle
			if (((rightPaddleSpeed < 0.f) && (rightPaddle.getPosition().y - paddleSize.y / 2 > 5.f)) ||
				((rightPaddleSpeed > 0.f) && (rightPaddle.getPosition().y + paddleSize.y / 2 < gameHeight - 5.f)))
			{
				rightPaddle.move(0.f, rightPaddleSpeed * deltaTime);
			}

			// Update the computer's paddle direction according to the ball position
			if (AITimer.getElapsedTime() > AITime)
			{
				AITimer.restart();
				if (ball.getPosition().y + ballRadius > rightPaddle.getPosition().y + paddleSize.y / 2)
					rightPaddleSpeed = paddleSpeed;
				else if (ball.getPosition().y - ballRadius < rightPaddle.getPosition().y - paddleSize.y / 2)
					rightPaddleSpeed = -paddleSpeed;
				else
					rightPaddleSpeed = 0.f;
			}

			// Move the ball
			float factor = ballSpeed * deltaTime;
			ball.move(std::cos(ballAngle) * factor, std::sin(ballAngle) * factor);

			// Check collision between ball and multiplier bonus
			if (multiplierVisible)
			{
				float dx = ball.getPosition().x - multiplierBonus.getPosition().x;
				float dy = ball.getPosition().y - multiplierBonus.getPosition().y;
				float distance = std::sqrt(dx * dx + dy * dy);

				if (distance < (ballRadius + multiplierBonus.getRadius()))
				{
					multiplierVisible = false;
					multiplierActive = true;
					multiplierDurationClock.restart();
					multiplierText.setString("Multiplier x" + std::to_string(currentMultiplier) + " (" +
						std::to_string(static_cast<int>(multiplierDuration)) + "s)");
				}
			}

			// Check collisions between the ball and the screen
			if (ball.getPosition().x - ballRadius < 0.f)
			{
				isPlaying = false;
				pauseMessage.setString("You lost!\nPress space to restart or\nescape to exit");
			}
			if (ball.getPosition().x + ballRadius > gameWidth)
			{
				isPlaying = false;
				pauseMessage.setString("You won!\nPress space to restart or\nescape to exit");
			}
			if (ball.getPosition().y - ballRadius < 0.f)
			{
				ballSound.play();
				ballAngle = -ballAngle;
				ball.setPosition(ball.getPosition().x, ballRadius + 0.1f);
			}
			if (ball.getPosition().y + ballRadius > gameHeight)
			{
				ballSound.play();
				ballAngle = -ballAngle;
				ball.setPosition(ball.getPosition().x, gameHeight - ballRadius - 0.1f);
			}

			// Check the collisions between the ball and the paddles
			// Left Paddle
			if (ball.getPosition().x - ballRadius < leftPaddle.getPosition().x + paddleSize.x / 2 &&
				ball.getPosition().x - ballRadius > leftPaddle.getPosition().x &&
				ball.getPosition().y + ballRadius >= leftPaddle.getPosition().y - paddleSize.y / 2 &&
				ball.getPosition().y - ballRadius <= leftPaddle.getPosition().y + paddleSize.y / 2)
			{
				if (ball.getPosition().y > leftPaddle.getPosition().y)
					ballAngle = pi - ballAngle + (std::rand() % 20) * pi / 180;
				else
					ballAngle = pi - ballAngle - (std::rand() % 20) * pi / 180;

				ballSound.play();
				ball.setPosition(leftPaddle.getPosition().x + ballRadius + paddleSize.x / 2 + 0.1f, ball.getPosition().y);

				// Increment the score when the player hits the ball (with multiplier if active)
				playerScore += 1 * currentMultiplier;
				scoreText.setString("Score: " + std::to_string(playerScore));

				// Update multiplier text with remaining time if active
				if (multiplierActive)
				{
					float remainingTime = multiplierDuration - multiplierDurationClock.getElapsedTime().asSeconds();
					multiplierText.setString("Multiplier x" + std::to_string(currentMultiplier) + " (" +
						std::to_string(static_cast<int>(remainingTime)) + "s)");
				}
			}

			// Right Paddle
			if (ball.getPosition().x + ballRadius > rightPaddle.getPosition().x - paddleSize.x / 2 &&
				ball.getPosition().x + ballRadius < rightPaddle.getPosition().x &&
				ball.getPosition().y + ballRadius >= rightPaddle.getPosition().y - paddleSize.y / 2 &&
				ball.getPosition().y - ballRadius <= rightPaddle.getPosition().y + paddleSize.y / 2)
			{
				if (ball.getPosition().y > rightPaddle.getPosition().y)
					ballAngle = pi - ballAngle + (std::rand() % 20) * pi / 180;
				else
					ballAngle = pi - ballAngle - (std::rand() % 20) * pi / 180;

				ballSound.play();
				ball.setPosition(rightPaddle.getPosition().x - ballRadius - paddleSize.x / 2 - 0.1f, ball.getPosition().y);
			}
		}

		// Clear the window
		window.clear(sf::Color(50, 200, 50));

		if (isPlaying)
		{
			// Draw the paddles and the ball
			window.draw(leftPaddle);
			window.draw(rightPaddle);
			window.draw(ball);
			window.draw(scoreText);
			window.draw(timeText);

			// Draw multiplier bonus if visible
			if (multiplierVisible)
			{
				// Animate multiplier bonus (pulsate)
				float scale = 0.8f + 0.2f * std::sin(gameClock.getElapsedTime().asSeconds() * 4);
				multiplierBonus.setScale(scale, scale);

				// Draw a text inside the bonus showing the multiplier value
				sf::Text bonusValueText;
				bonusValueText.setFont(font);
				bonusValueText.setCharacterSize(20);
				bonusValueText.setString("x" + std::to_string(currentMultiplier));
				bonusValueText.setFillColor(sf::Color::Black);
				// Center the text in the bonus
				sf::FloatRect textBounds = bonusValueText.getLocalBounds();
				bonusValueText.setOrigin(textBounds.width / 2, textBounds.height / 2);
				bonusValueText.setPosition(multiplierBonus.getPosition());

				window.draw(multiplierBonus);
				window.draw(bonusValueText);
			}

			// Draw active multiplier text
			if (multiplierActive)
			{
				window.draw(multiplierText);
			}
		}
		else
		{
			// Draw the pause message
			window.draw(pauseMessage);

			// Draw the score even when paused (except at the start of the game)
			if (pauseMessage.getString() != "Welcome to SFML pong!\nPress space to start the game")
			{
				window.draw(scoreText);
				window.draw(timeText);
			}
		}

		// Display things on screen
		window.display();
	}

	return EXIT_SUCCESS;
}


// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file