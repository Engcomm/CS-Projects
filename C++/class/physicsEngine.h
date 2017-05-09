#ifndef GIZMOBALL_PHYSICS_ENGINE_H
#define GIZMOBALL_PHYSICS_ENGINE_H

#include <vector>
#include <cmath>
#include "physicsObject.h"

namespace gizmoball {

struct moveRecord {
	std::string id; // Object id
	double moveX; // move x distance
	double moveY; // move y distance
}; // struct move record

class PhysicsEngine {
 public:
	void addObject(PhysicsObject physicsObject);
	void removeById(std::string id);
	void setSpeed(std::string id, object_speed speed);
	void setBorder(std::string id, object_border border);
	void setFixed(std::string id, bool fixed);
    void removeAll();
    PhysicsObject* getById(std::string id);
	std::vector<moveRecord> getNext();
 private:
 	object_border checkCollision(PhysicsObject o1, PhysicsObject o2);
	void bump(PhysicsObject &o1, PhysicsObject &o2, object_border border);
	std::vector<PhysicsObject> objects;
}; // class PhysicsEngine

} // namespace gizmoball

#endif
